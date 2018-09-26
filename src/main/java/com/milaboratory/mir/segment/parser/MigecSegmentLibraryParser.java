package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.StringArrayIndexer;
import com.milaboratory.mir.segment.SegmentType;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class MigecSegmentLibraryParser {
    private static final String SEP = "\t";

    private static String[] safeSplit(String line) {
        String[] splitLine = line.split(SEP);
        if (splitLine.length != 10) {
            throw new RuntimeException("Bad line in input file provided to MIGEC parser: '" +
                    line + "'. Lines should contain 10 tab-separated columns.");
        }
        return splitLine;
    }

    private MigecSegmentLibraryParser() {

    }

    public static SegmentLibraryImpl load(InputStream data, Species species, Gene gene) throws IOException {
        return load(data, species, gene, true);
    }

    public static SegmentLibraryImpl load(InputStream data, Species species, Gene gene,
                                          boolean fixDV) throws IOException {
        Map<String, VariableSegment> variableSegmentMap = new HashMap<>();
        Map<String, DiversitySegment> diversitySegmentMap = new HashMap<>();
        Map<String, JoiningSegment> joiningSegmentMap = new HashMap<>();
        Map<String, ConstantSegment> constantSegmentMap = new HashMap<>();
        Map<String, String> majorAlleleAliases = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(data))) {
            String line = br.readLine();
            if (line == null) {
                throw new RuntimeException("Cannot parse MIGEC library: empty input.");
            }

            HeaderInfo H = new HeaderInfo(safeSplit(line));

            String[] splitLine;

            while ((line = br.readLine()) != null) {
                splitLine = safeSplit(line);
                if (species.matches(splitLine[H.speciesColIndex]) &&
                        gene.matches(splitLine[H.geneColIndex])) {
                    SegmentType segmentType = SegmentType.byAlias(splitLine[H.segmentColIndex]);
                    String id = splitLine[H.idColIndex];
                    NucleotideSequence seq = new NucleotideSequence(splitLine[H.sequenceColIndex]);

                    if (seq.containsWildcards())
                        continue; // todo: add warning

                    majorAlleleAliases.put(id, id.split("\\*")[0] + "*01");
                    boolean majorAllele = id.endsWith("*01");

                    switch (segmentType) {
                        case V:
                            int cdr1Start = Integer.parseInt(splitLine[H.cdr1StartColIndex]);
                            int cdr1End = Integer.parseInt(splitLine[H.cdr1EndColIndex]);
                            int cdr2Start = Integer.parseInt(splitLine[H.cdr2StartColIndex]);
                            int cdr2End = Integer.parseInt(splitLine[H.cdr2EndColIndex]);
                            int referencePoint = Integer.parseInt(splitLine[H.refPointColIndex]);

                            Consumer<String> putV = id1 -> variableSegmentMap.put(id1,
                                    new VariableSegmentImpl(
                                            id1, seq,
                                            Math.max(0, cdr1Start), Math.max(0, cdr1End),
                                            Math.max(0, cdr2Start), Math.max(0, cdr2End),
                                            referencePoint, majorAllele)
                            );

                            putV.accept(id);

                            if (fixDV && id.contains("/DV")) { // account for MIXCR naming convention
                                id = id.replaceAll("/DV", "DV");
                                majorAlleleAliases.put(id, id.split("\\*")[0] + "*01");
                                putV.accept(id);
                            }
                            break;

                        case D:
                            DiversitySegment diversitySegment = new DiversitySegmentImpl(
                                    id, seq, majorAllele
                            );
                            diversitySegmentMap.put(id, diversitySegment);
                            break;

                        case J:
                            referencePoint = Integer.parseInt(splitLine[H.refPointColIndex]);

                            JoiningSegment joiningSegment = new JoiningSegmentImpl(
                                    id, seq, referencePoint, majorAllele
                            );
                            joiningSegmentMap.put(id, joiningSegment);
                            break;

                        case C:
                            constantSegmentMap.put(id, new ConstantSegmentImpl(
                                    id, seq, majorAllele
                            ));
                            break;
                    }
                }
            }
        }

        return new SegmentLibraryImpl(species, gene,
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap,
                majorAlleleAliases);
    }


    private static class HeaderInfo {
        final int speciesColIndex, geneColIndex, segmentColIndex, idColIndex, refPointColIndex,
                sequenceColIndex, cdr1StartColIndex, cdr1EndColIndex, cdr2StartColIndex, cdr2EndColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            this.speciesColIndex = headerParser.getIndexOf("species");
            this.geneColIndex = headerParser.getIndexOf("gene");
            this.segmentColIndex = headerParser.getIndexOf("segment");
            this.idColIndex = headerParser.getIndexOf("id");
            this.refPointColIndex = headerParser.getIndexOf("reference_point");
            this.sequenceColIndex = headerParser.getIndexOf("sequence");
            this.cdr1StartColIndex = headerParser.getIndexOf("cdr1.start");
            this.cdr1EndColIndex = headerParser.getIndexOf("cdr1.end");
            this.cdr2StartColIndex = headerParser.getIndexOf("cdr2.start");
            this.cdr2EndColIndex = headerParser.getIndexOf("cdr2.end");
        }
    }
}