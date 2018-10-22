package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.segment.Species;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class MhcAlleleLibraryUtils {
    private static final int NUM_COLUMNS = 10;
    private static final String HEADER = "species\tmhc.class\tchain\tallele\tgroove.start\thelix1.start\thelix2.start\thelix3.start\tmembrane.start";

    public static MhcAlleleLibrary parse(InputStream inputStream, Species species, MhcClassType mhcClassType) {
        Map<String, MhcAllele> mhcAlleles = new HashMap<>();
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

                if (species.matches(splitLine[0]) && mhcClassType.matches(splitLine[1])) {
                    MhcChainType mhcChainType;

                    String mhcChainTypeStr = splitLine[2];
                    if (MhcChainType.Alpha.toString().equalsIgnoreCase(mhcChainTypeStr)) {
                        mhcChainType = MhcChainType.Alpha;
                    } else if (MhcChainType.Beta.toString().equalsIgnoreCase(mhcChainTypeStr)) {
                        mhcChainType = MhcChainType.Beta;
                    } else {
                        throw new RuntimeException("Cannot parse MHC chain type " + mhcChainTypeStr);
                    }

                    String id = splitLine[3];

                    // groove.start	helix1.start	helix2.start	helix3.start	membrane.start

                    AminoAcidSequence sequence = new AminoAcidSequence(splitLine[9]);

                    var markup = new ArrayBasedSequenceRegionMarkup<>(
                            sequence,
                            new int[]{
                                    0,                              // start
                                    Integer.parseInt(splitLine[4]), // groove
                                    Integer.parseInt(splitLine[5]), // helix1
                                    Integer.parseInt(splitLine[6]), // helix2
                                    Integer.parseInt(splitLine[7]), // helix3
                                    Integer.parseInt(splitLine[8]), // membrane
                                    sequence.size()                 // end
                            },
                            MhcRegionType.class
                    ).asPrecomputed();

                    mhcAlleles.put(id, new MhcAllele(id, mhcChainType, mhcClassType, markup));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new MhcAlleleLibrary(mhcClassType, species, mhcAlleles);
    }
}
