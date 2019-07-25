package com.antigenomics.mir.mhc;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.Species;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MhcAlleleLibraryUtils {
    public static final String PATH = "mhc/mhc_serotype_prot.txt";

    private static final Map<SpeciesMhcClassTuple, MhcAlleleLibrary<MhcAlleleWithSequence>> resourceLibraryCache = new ConcurrentHashMap<>();

    private MhcAlleleLibraryUtils() {

    }

    public static MhcAlleleLibrary<MhcAlleleWithSequence> getLibraryFromResources(Species species, MhcClassType mhcClassType) {
        return resourceLibraryCache.computeIfAbsent(
                new SpeciesMhcClassTuple(species, mhcClassType),
                x -> {
                    try {
                        return MhcAlleleLibraryParser.parse(CommonUtils.getResourceAsStream(PATH), species, mhcClassType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static MhcAlleleLibrary<MhcAlleleWithSequence> getLibraryFromFile(String path, Species species, MhcClassType mhcClassType) throws IOException {
        return MhcAlleleLibraryParser.parse(new FileInputStream(path), species, mhcClassType);
    }

    public static MhcAlelleLibraryBundle<MhcAlleleImpl, MockMhcAlleleLibrary> getMockMhcLibraryBundle() {
        Map<SpeciesMhcClassTuple, MockMhcAlleleLibrary> mhcLibraryMap = new HashMap<>();

        for (Species species : Arrays.asList(Species.Human, Species.Mouse, Species.Monkey)) {
            for (MhcClassType mhcClassType : Arrays.asList(MhcClassType.MHCI, MhcClassType.MHCII)) {
                mhcLibraryMap.put(new SpeciesMhcClassTuple(species, mhcClassType),
                        new MockMhcAlleleLibrary(mhcClassType, species));
            }
        }

        return new MhcAlelleLibraryBundle<>(mhcLibraryMap);
    }

    public static MhcAlelleLibraryBundle<MhcAlleleWithSequence, MhcAlleleLibrary<MhcAlleleWithSequence>> getBuiltinMhcLibraryBundle() {
        Map<SpeciesMhcClassTuple, MhcAlleleLibrary<MhcAlleleWithSequence>> mhcLibraryMap = new HashMap<>();

        for (Species species : Arrays.asList(Species.Human, Species.Mouse)) {
            for (MhcClassType mhcClassType : Arrays.asList(MhcClassType.MHCI, MhcClassType.MHCII)) {
                mhcLibraryMap.put(new SpeciesMhcClassTuple(species, mhcClassType),
                        MhcAlleleLibraryUtils.getLibraryFromResources(species, mhcClassType));
            }
        }

        return new MhcAlelleLibraryBundle<>(mhcLibraryMap);
    }
}
