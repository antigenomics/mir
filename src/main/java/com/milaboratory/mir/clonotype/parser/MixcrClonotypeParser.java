package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.StringArrayIndexer;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.clonotype.rearrangement.SegmentTrimming;
import com.milaboratory.mir.segment.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MixcrClonotypeParser extends AbstractClonotypeTableParser<ReadlessClonotypeImpl> {
    private static final int RP_CDR3_BEGIN = 9,
            RP_V_END_TRIMMED = 11,
            RP_D_START_TRIMMED = 12,
            RP_D_END_TRIMMED = 15,
            RP_J_START_TRIMMED = 16,
            RP_V_DEL = 10,
            RP_D5_DEL = 13,
            RP_D3_DEL = 14,
            RP_J_DEL = 17;

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
    public ClonotypeCall<ReadlessClonotypeImpl> parse(String[] splitLine) {
        // todo: ask Dima if its long or int
        int id = Integer.parseInt(splitLine[headerInfo.cloneIdColIndex]);
        int count = Math.round(Float.parseFloat(splitLine[headerInfo.countColIndex]));
        double freq = Double.parseDouble(splitLine[headerInfo.freqColIndex]);

        NucleotideSequence cdr3Nt = new NucleotideSequence(splitLine[headerInfo.cdr3NtColIndex]);

        AminoAcidSequence cdr3Aa = headerInfo.cdr3AaColIndex == -1 ?
                AminoAcidSequence.translateFromCenter(cdr3Nt) :
                new AminoAcidSequence(splitLine[headerInfo.cdr3AaColIndex]);

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
        String[] refPoints = splitLine[headerInfo.refPointColIndex].split("[,:]");

        // todo: handle cases when critical fields are missing, e.g. CDR3 ref points -- show skip & issue a warning
        int cdr3Start = Integer.parseInt(refPoints[RP_CDR3_BEGIN]),
                vEnd = parseJunctionMarkup(refPoints[RP_V_END_TRIMMED], cdr3Start),
                jStart = parseJunctionMarkup(refPoints[RP_J_START_TRIMMED], cdr3Start),
                dStart = parseJunctionMarkup(refPoints[RP_D_START_TRIMMED], cdr3Start),
                dEnd = parseJunctionMarkup(refPoints[RP_D_END_TRIMMED], cdr3Start);
        JunctionMarkup junctionMarkup = new JunctionMarkup(vEnd, jStart, dStart, dEnd);

        SegmentTrimming segmentTrimming = new SegmentTrimming(
                parseSegmentTrimming(refPoints[RP_V_DEL]),
                parseSegmentTrimming(refPoints[RP_J_DEL]),
                parseSegmentTrimming(refPoints[RP_D5_DEL]),
                parseSegmentTrimming(refPoints[RP_D3_DEL]));

        // todo: also parse contig
        return new ClonotypeCall<>(id,
                count, freq,
                new ReadlessClonotypeImpl(cdr3Nt,
                        vCalls, dCalls, jCalls, cCalls,
                        segmentTrimming, junctionMarkup,
                        cdr3Aa)
        );
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
                refPointColIndex;

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
        }
    }
}
