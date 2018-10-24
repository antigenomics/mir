package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.CommonUtils;
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
    private static final String HEADER = "species\tmhc.class\tchain\tallele\tsignal.start\tregion1.start\tregion2.start\tregion3.start\tmembrane.start";
    private static final String RESOURCE_PATH = "mhc/mhc_serotype_prot.txt";

    public static MhcAlleleLibrary load(Species species, MhcClassType mhcClassType, MhcChainType mhcChainType) {
        try {
            return parse(CommonUtils.getResourceAsStream(RESOURCE_PATH),
                    species,
                    mhcClassType,
                    mhcChainType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MhcAlleleLibrary parse(InputStream inputStream, Species species,
                                         MhcClassType mhcClassType, MhcChainType mhcChainType) throws IOException {
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

                if (species.matches(splitLine[0]) &&
                        mhcClassType.matches(splitLine[1]) &&
                        mhcChainType.matches(splitLine[2])) {

                    String id = splitLine[3];

                    // groove.start	helix1.start	helix2.start	helix3.start	membrane.start

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

                    mhcAlleles.put(id, new MhcAllele(id, mhcChainType, mhcClassType, markup));
                }
            }
        }

        return new MhcAlleleLibrary(mhcClassType, species, mhcAlleles);
    }
}
