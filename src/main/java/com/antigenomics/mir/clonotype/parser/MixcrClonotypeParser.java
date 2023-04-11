package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.rearrangement.ClonotypeWithReadImpl;
import com.antigenomics.mir.clonotype.rearrangement.JunctionMarkup;
import com.antigenomics.mir.clonotype.rearrangement.SegmentTrimming;
import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.antigenomics.mir.segment.*;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.StringArrayIndexer;
import com.antigenomics.mir.clonotype.ClonotypeCall;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MixcrClonotypeParser extends AbstractClonotypeTableParser<ClonotypeWithReadImpl> {
    private static final int RP_CDR3_BEGIN = 9,
            RP_V_END_TRIMMED = 11,
            RP_D_START_TRIMMED = 12,
            RP_D_END_TRIMMED = 15,
            RP_J_START_TRIMMED = 16,
            RP_V_DEL = 10,
            RP_D5_DEL = 13,
            RP_D3_DEL = 14,
            RP_J_DEL = 17,
            RP_FR1_BEGIN = 4,
            RP_FR1_END = 5,
            RP_CDR1_END = 6,
            RP_FR2_END = 7,
            RP_CDR2_END = 8,
            RP_CDR3_END = 18,
            RP_FR4_END = 19;

    private final HeaderInfo headerInfo;
    private final boolean convertAlleles;

    public MixcrClonotypeParser(String[] header,
                                SegmentLibrary segmentLibrary,
                                boolean majorAlleles) {
        this(header, segmentLibrary, majorAlleles, true);
    }

    public MixcrClonotypeParser(String[] header,
                                SegmentLibrary segmentLibrary,
                                boolean majorAlleles,
                                boolean convertAlleles) {
        super(header, segmentLibrary, majorAlleles);
        this.headerInfo = new HeaderInfo(header);
        this.convertAlleles = convertAlleles;
    }

    @Override
    public ClonotypeCall<ClonotypeWithReadImpl> parse(String[] splitLine) {
        // Quantity info
        // todo: ask Dima if its long or int
        int id = Integer.parseInt(splitLine[headerInfo.cloneIdColIndex]);
        long count = Math.round(Float.parseFloat(splitLine[headerInfo.countColIndex]));
        double freq = Double.parseDouble(splitLine[headerInfo.freqColIndex]);

        // Segment calls
        List<SegmentCall<VariableSegment>> vCalls = streamSegmentCalls(splitLine, headerInfo.vColIndex)
                .map(x -> getV(x[0], Float.parseFloat(x[1])))
                .collect(Collectors.toList());

        List<SegmentCall<DiversitySegment>> dCalls = streamSegmentCalls(splitLine, headerInfo.dColIndex)
                .map(x -> getD(x[0], Float.parseFloat(x[1])))
                .collect(Collectors.toList());

        List<SegmentCall<JoiningSegment>> jCalls = streamSegmentCalls(splitLine, headerInfo.jColIndex)
                .map(x -> getJ(x[0], Float.parseFloat(x[1])))
                .collect(Collectors.toList());

        List<SegmentCall<ConstantSegment>> cCalls = streamSegmentCalls(splitLine, headerInfo.cColIndex)
                .map(x -> getC(x[0], Float.parseFloat(x[1])))
                .collect(Collectors.toList());

        // even if there are several ref point arrays joined by "," it will use first
        // todo: consider multiple comma-separated targetSequences

        // Reference points/markup
        String refPointsLine = splitLine[headerInfo.refPointColIndex];
        boolean multiPartAlignment = refPointsLine.contains(",");
        JunctionMarkup junctionMarkup;
        SegmentTrimming segmentTrimming;

        List<SequenceRegion<NucleotideSequence, AntigenReceptorRegionType>> regions = new ArrayList<>();
        if (multiPartAlignment) {
            // Cannot parse meaningfully here
            // todo: issue a warning
            NucleotideSequence cdr3Nt = new NucleotideSequence(splitLine[headerInfo.cdr3NtColIndex]);
            regions.add(SequenceRegion.full(AntigenReceptorRegionType.CDR3, cdr3Nt));
            junctionMarkup = JunctionMarkup.DUMMY;
            segmentTrimming = SegmentTrimming.DUMMY;
        } else {
            // todo: handle cases when critical fields are missing,
            //  e.g. CDR3 ref points -- show skip & issue a warning

            String[] refPoints = refPointsLine.split(":");

            int cdr3Start = Integer.parseInt(refPoints[RP_CDR3_BEGIN]),
                    vEnd = parseJunctionMarkup(refPoints[RP_V_END_TRIMMED], cdr3Start),
                    jStart = parseJunctionMarkup(refPoints[RP_J_START_TRIMMED], cdr3Start),
                    dStart = parseJunctionMarkup(refPoints[RP_D_START_TRIMMED], cdr3Start),
                    dEnd = parseJunctionMarkup(refPoints[RP_D_END_TRIMMED], cdr3Start);

            junctionMarkup = new JunctionMarkup(vEnd, jStart, dStart, dEnd);

            int vDel = parseSegmentTrimming(refPoints[RP_V_DEL]),
                    jDel = parseSegmentTrimming(refPoints[RP_J_DEL]),
                    d5Del = parseSegmentTrimming(refPoints[RP_D5_DEL]),
                    d3Del = parseSegmentTrimming(refPoints[RP_D3_DEL]);

            segmentTrimming = new SegmentTrimming(vDel, jDel, d5Del, d3Del);

            if (headerInfo.targetSequenceColIndex != -1 && refPoints.length > RP_FR4_END) {
                NucleotideSequence fullSequence = new NucleotideSequence(splitLine[headerInfo.targetSequenceColIndex]);
                updateRegions(regions, fullSequence, refPoints[RP_FR1_BEGIN], refPoints[RP_FR1_END], AntigenReceptorRegionType.FR1);
                updateRegions(regions, fullSequence, refPoints[RP_FR1_END], refPoints[RP_CDR1_END], AntigenReceptorRegionType.CDR1);
                updateRegions(regions, fullSequence, refPoints[RP_CDR1_END], refPoints[RP_FR2_END], AntigenReceptorRegionType.FR2);
                updateRegions(regions, fullSequence, refPoints[RP_FR2_END], refPoints[RP_CDR2_END], AntigenReceptorRegionType.CDR2);
                updateRegions(regions, fullSequence, refPoints[RP_CDR2_END], refPoints[RP_CDR3_BEGIN], AntigenReceptorRegionType.FR3);
                updateRegions(regions, fullSequence, refPoints[RP_CDR3_BEGIN], refPoints[RP_CDR3_END], AntigenReceptorRegionType.CDR3);
                updateRegions(regions, fullSequence, refPoints[RP_CDR3_END], refPoints[RP_FR4_END], AntigenReceptorRegionType.FR4);
            } else {
                NucleotideSequence cdr3Nt = new NucleotideSequence(splitLine[headerInfo.cdr3NtColIndex]);
                regions.add(SequenceRegion.full(AntigenReceptorRegionType.CDR3, cdr3Nt));
            }
        }

        // Finally markup
        PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> markup =
                PrecomputedSequenceRegionMarkup.someRegions(
                        regions,
                        NucleotideSequence.ALPHABET, AntigenReceptorRegionType.class);

        // Mutations
        Mutations<NucleotideSequence> vMutations = headerInfo.vAlignmentsColIndex == -1 ? Mutations.EMPTY_NUCLEOTIDE_MUTATIONS :
                getMutations(splitLine[headerInfo.vAlignmentsColIndex]),
                dMutations = headerInfo.dAlignmentsColIndex == -1 ? Mutations.EMPTY_NUCLEOTIDE_MUTATIONS :
                        getMutations(splitLine[headerInfo.dAlignmentsColIndex]),
                jMutations = headerInfo.jAlignmentsColIndex == -1 ? Mutations.EMPTY_NUCLEOTIDE_MUTATIONS :
                        getMutations(splitLine[headerInfo.jAlignmentsColIndex]),
                cMutations = headerInfo.cAlignmentsColIndex == -1 ? Mutations.EMPTY_NUCLEOTIDE_MUTATIONS :
                        getMutations(splitLine[headerInfo.cAlignmentsColIndex]);

        return new ClonotypeCall<>(id,
                count, freq,
                new ClonotypeWithReadImpl(
                        markup,
                        vMutations, dMutations, jMutations, cMutations,
                        vCalls, dCalls, jCalls, cCalls,
                        segmentTrimming, junctionMarkup)
        );
    }

    private static Mutations<NucleotideSequence> getMutations(String alignmentString) {
        String[] splitStr = alignmentString.split("[|;]");
        if (splitStr.length < 6 || splitStr[0].length() == 0 || splitStr[5].length() == 0) {
            return Mutations.EMPTY_NUCLEOTIDE_MUTATIONS;
        }
        int targetFrom = Integer.parseInt(splitStr[0]);
        String mutationString = splitStr[5];

        return Mutations.decodeNuc(mutationString).move(targetFrom);
    }

    private static void updateRegions(List<SequenceRegion<NucleotideSequence, AntigenReceptorRegionType>> regions,
                                      NucleotideSequence fullSequence, String start, String end,
                                      AntigenReceptorRegionType regionType) {
        if (!start.isEmpty() && !end.isEmpty()) {
            int startInt = Integer.parseInt(start), endInt = Integer.parseInt(end);
            if (startInt >= 0 && endInt >= 0 && startInt < endInt) {
                regions.add(new SequenceRegion<>(regionType, fullSequence.getRange(startInt, endInt),
                        startInt, endInt));
            }
        }
    }

    private Stream<String[]> streamSegmentCalls(String[] splitLine, int index) {
        return index == -1 ? Stream.empty() : streamSegmentCalls(splitLine[index]);
    }

    private Stream<String[]> streamSegmentCalls(String str) {
        if (str.isEmpty()) {
            return Stream.empty();
        }
        if (convertAlleles) {
            str = str.replaceAll("\\*00", "*01");
        }
        return Arrays.stream(str.split(",")).map(x -> x.split("[\\(\\)]"));
    }

    private static int parseJunctionMarkup(String str, int cdr3Start) {
        return str.isEmpty() ? -1 : (Integer.parseInt(str) - cdr3Start);
    }

    private static int parseSegmentTrimming(String str) {
        // MIXCR convention: negative deletions = -#removed bases, positive = P segment size
        // Murugan convention: positive = #removed bases, negative = -P segment size
        // We use Murugan convention
        return str.isEmpty() ? 0 : -Integer.parseInt(str);
    }

    private static class HeaderInfo {
        final int cloneIdColIndex,
                countColIndex, freqColIndex,
                cdr3NtColIndex, cdr3AaColIndex,
                vColIndex, dColIndex, jColIndex, cColIndex,
                refPointColIndex,
                targetSequenceColIndex,
                vAlignmentsColIndex,
                dAlignmentsColIndex,
                jAlignmentsColIndex,
                cAlignmentsColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            this.cloneIdColIndex = headerParser.getIndexOf(new String[]{"cloneId", "Clone ID"});
            this.countColIndex = headerParser.getIndexOf(new String[]{"cloneCount", "Clone count"});
            this.freqColIndex = headerParser.getIndexOfS(new String[]{"cloneFraction", "Clone fraction"});
            this.cdr3NtColIndex = headerParser.getIndexOf(new String[]{"nSeqCDR3", "N. Seq. CDR3"});
            this.cdr3AaColIndex = headerParser.getIndexOf(new String[]{"aaSeqCDR3", "AA. Seq. CDR3"}, false);
            this.vColIndex = headerParser.getIndexOf(new String[]{"allVHitsWithScore", "All V Hits With Score ", "All V hits"}, false);
            this.dColIndex = headerParser.getIndexOf(new String[]{"allDHitsWithScore", "All D Hits With Score ", "All D hits"}, false);
            this.jColIndex = headerParser.getIndexOf(new String[]{"allJHitsWithScore", "All J Hits With Score ", "All J hits"}, false);
            this.cColIndex = headerParser.getIndexOf(new String[]{"allCHitsWithScore", "All C Hits With Score ", "All C hits"}, false);
            this.refPointColIndex = headerParser.getIndexOf(new String[]{"refPoints", "Ref. points"});
            this.targetSequenceColIndex = headerParser.getIndexOf(new String[]{"targetSequences", "clonalSequence", "Clonal sequence(s)"}, false);
            this.vAlignmentsColIndex = headerParser.getIndexOf(new String[]{"allVAlignments", "All V Alignments"}, false);
            this.dAlignmentsColIndex = headerParser.getIndexOf(new String[]{"allDAlignments", "All D Alignments"}, false);
            this.jAlignmentsColIndex = headerParser.getIndexOf(new String[]{"allJAlignments", "All J Alignments"}, false);
            this.cAlignmentsColIndex = headerParser.getIndexOf(new String[]{"allCAlignments", "All C Alignments"}, false);
        }
    }
}
