package com.milaboratory.mir.segment.parser;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.segment.SegmentLibraryImpl;

import java.io.FileInputStream;
import java.io.IOException;

public class MigecSegmentLibraryUtils {
    public static final String PATH = "segments.txt";

    private MigecSegmentLibraryUtils() {

    }

    public static SegmentLibraryImpl getLibraryFromResources(Species species, Gene gene) throws IOException {
        return MigecSegmentLibraryParser.load(CommonUtils.getResourceAsStream(PATH), species, gene);
    }

    public static SegmentLibraryImpl getLibraryFromFile(String path, Species species, Gene gene) throws IOException {
        return MigecSegmentLibraryParser.load(new FileInputStream(path), species, gene);
    }
}
