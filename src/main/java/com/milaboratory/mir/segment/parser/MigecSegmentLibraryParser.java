package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Gene;
import com.milaboratory.mir.HeaderParser;
import com.milaboratory.mir.SegmentType;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.segment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

final class MigecSegmentLibraryParser {
    private static final String SEP = "[\t ]+";

    private MigecSegmentLibraryParser() {

    }

    public static SegmentLibraryImpl load(InputStream data, Species species, Gene gene) throws IOException {
        Map<String, VariableSegment> variableSegmentMap = new HashMap<>();
        Map<String, DiversitySegment> diversitySegmentMap = new HashMap<>();
        Map<String, JoiningSegment> joiningSegmentMap = new HashMap<>();
        Map<String, ConstantSegment> constantSegmentMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(data))) {
            String line = br.readLine();
            if (line == null) {
                throw new RuntimeException("Cannot parse MIGEC library: empty input.");
            }

            HeaderInfo H = new HeaderInfo(line.split(SEP));

            String[] splitLine;

            while ((line = br.readLine()) != null) {
                splitLine = line.split(SEP);
                if (species.matches(splitLine[H.speciesColIndex]) &&
                        gene.matches(splitLine[H.geneColIndex])) {
                    SegmentType segmentType = SegmentType.byAlias(splitLine[H.segmentColIndex]);
                    String id = splitLine[H.idColIndex];
                    NucleotideSequence seq = new NucleotideSequence(splitLine[H.sequenceColIndex]);

                    switch (segmentType) {
                        case V:
                            int cdr1Start = Integer.parseInt(splitLine[H.cdr1StartColIndex]);
                            int cdr1End = Integer.parseInt(splitLine[H.cdr1StartColIndex]);
                            int cdr2Start = Integer.parseInt(splitLine[H.cdr2EndColIndex]);
                            int cdr2End = Integer.parseInt(splitLine[H.cdr2EndColIndex]);
                            int referencePoint = Integer.parseInt(splitLine[H.refPointColIndex]);

                            VariableSegment variableSegment = new VariableSegmentImpl(
                                    id, seq, cdr1Start, cdr1End, cdr2Start, cdr2End, referencePoint
                            );
                            variableSegmentMap.put(id, variableSegment);
                            break;

                        case D:
                            DiversitySegment diversitySegment = new DiversitySegmentImpl(
                                    id, seq
                            );
                            diversitySegmentMap.put(id, diversitySegment);
                            break;

                        case J:
                            referencePoint = Integer.parseInt(splitLine[H.refPointColIndex]);

                            JoiningSegment joiningSegment = new JoiningSegmentImpl(
                                    id, seq, referencePoint
                            );
                            joiningSegmentMap.put(id, joiningSegment);
                            break;

                        case C:
                            constantSegmentMap.put(id, new ConstantSegmentImpl(id, seq));
                            break;
                    }
                }
            }
        }

        return new SegmentLibraryImpl(species, gene,
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap);
    }


    private static class HeaderInfo {
        final int speciesColIndex, geneColIndex, segmentColIndex, idColIndex, refPointColIndex,
                sequenceColIndex, cdr1StartColIndex, cdr1EndColIndex, cdr2StartColIndex, cdr2EndColIndex;

        HeaderInfo(String[] header) {
            HeaderParser headerParser = new HeaderParser(header);
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