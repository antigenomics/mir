package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Gene;
import com.milaboratory.mir.SegmentType;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.segment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MigecSegmentLibraryParser {
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

                            break;

                        case D:
                            break;

                        case J:
                            break;

                        case C:
                            break;
                    }
                }
            }
        }

        return new SegmentLibraryImpl(species, gene,
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap);
    }


    private static class HeaderInfo {
        private final List<String> headerList;
        final int speciesColIndex, geneColIndex, segmentColIndex, idColIndex, refPointColIndex,
                sequenceColIndex, cdr1StartColIndex, cdr1EndColIndex, cdr2StartColIndex, cdr2EndColIndex;

        HeaderInfo(String[] header) {
            this.headerList = Arrays.stream(header).map(String::toLowerCase).collect(Collectors.toList());
            this.speciesColIndex = getIndexOf("species");
            this.geneColIndex = getIndexOf("gene");
            this.segmentColIndex = getIndexOf("segment");
            this.idColIndex = getIndexOf("id");
            this.refPointColIndex = getIndexOf("reference_point");
            this.sequenceColIndex = getIndexOf("sequence");
            this.cdr1StartColIndex = getIndexOf("cdr1.start");
            this.cdr1EndColIndex = getIndexOf("cdr1.end");
            this.cdr2StartColIndex = getIndexOf("cdr2.start");
            this.cdr2EndColIndex = getIndexOf("cdr2.end");
        }

        private int getIndexOf(String name) {
            int res = headerList.indexOf(name);
            if (res < 0) {
                throw new RuntimeException("Failed to pass header in MIGEC segment library, " +
                        "missing column '" + name + "'");
            }
            return res;
        }
    }
}