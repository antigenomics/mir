package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;
import com.antigenomics.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.antigenomics.mir.mhc.*;
import com.antigenomics.mir.structure.MhcRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class MhcAlleleLibraryParser {
    private static final int NUM_COLUMNS = 10;
    private static final String HEADER = "species\tmhc.class\tchain\tallele\tsignal.start\tregion1.start\tregion2.start\tregion3.start\tmembrane.start";

    private MhcAlleleLibraryParser() {

    }

    public static MhcAlleleLibrary<MhcAlleleWithSequence> parse(InputStream inputStream, Species species,
                                                                MhcClassType mhcClassType) throws IOException {
        Map<String, MhcAlleleWithSequence> mhcAlleles = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            boolean firstLine = true;
            String line;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    if (!line.startsWith(HEADER)) {
                        throw new RuntimeException("Bad header:\n" + line + "\n- should start with:\n" + HEADER);
                    }

                    firstLine = false;
                    continue;
                }

                String[] splitLine = line.split("\t");

                if (splitLine.length < NUM_COLUMNS) {
                    throw new RuntimeException("Cannot parse MHC allele line, bad number of columns in line:\n" +
                            Arrays.toString(splitLine));
                }

                if (species.matches(splitLine[0]) &&
                        mhcClassType.matches(splitLine[1])) {

                    String id = splitLine[3];
                    String mhcChainTypeStr = splitLine[2];
                    MhcChainType mhcChainType = null;
                    if (MhcChainType.MHCa.matches(mhcChainTypeStr)) {
                        mhcChainType = MhcChainType.MHCa;
                    } else if (MhcChainType.MHCb.matches(mhcChainTypeStr)) {
                        mhcChainType = MhcChainType.MHCb;
                    } else {
                        throw new RuntimeException("Cannot parse MHC chain type " + mhcChainTypeStr);
                    }

                    AminoAcidSequence sequence = new AminoAcidSequence(splitLine[9]);

                    var markup = new ArrayBasedSequenceRegionMarkup<>(
                            sequence,
                            new int[]{
                                    Integer.parseInt(splitLine[4]), // signal   start
                                    Integer.parseInt(splitLine[5]), // region1   start
                                    Integer.parseInt(splitLine[6]), // region2   start
                                    Integer.parseInt(splitLine[7]), // region3   start
                                    Integer.parseInt(splitLine[8]), // membrane start
                                    sequence.size()                 // end
                            },
                            MhcRegionType.class
                    );

                    mhcAlleles.put(id, new MhcAlleleWithSequence(id, mhcChainType, mhcClassType, species, markup));
                }
            }
        }

        return new MhcAlleleLibraryImpl<>(mhcClassType, species, mhcAlleles);
    }
}
