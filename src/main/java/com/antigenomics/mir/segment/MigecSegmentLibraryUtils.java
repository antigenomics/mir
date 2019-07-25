package com.antigenomics.mir.segment;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.Species;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MigecSegmentLibraryUtils {
    public static final String PATH = "segments.txt";

    private static final Map<SpeciesGeneTuple, SegmentLibraryImpl> resourceLibraryCache = new ConcurrentHashMap<>();

    private MigecSegmentLibraryUtils() {

    }

    public static SegmentLibraryImpl getLibraryFromResources(Species species, Gene gene) {
        return resourceLibraryCache.computeIfAbsent(
                new SpeciesGeneTuple(species, gene),
                x -> {
                    try {
                        return MigecSegmentLibraryParser.parse(CommonUtils.getResourceAsStream(PATH), species, gene);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static SegmentLibraryImpl getLibraryFromFile(String path, Species species, Gene gene) throws IOException {
        return MigecSegmentLibraryParser.parse(new FileInputStream(path), species, gene);
    }
}
