package com.milaboratory.mir.segment.parser;

import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import org.junit.Test;

import java.io.IOException;

public class MigecSegmentLibraryUtilsTest {
    @Test
    public void testRes() throws IOException {
        System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA));
        System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB));
        System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(Species.Mouse, Gene.TRA));
        System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(Species.Mouse, Gene.TRB));
    }
}