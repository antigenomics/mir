package com.milaboratory.mir.segment.parser;

import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class MigecSegmentLibraryUtilsTest {
    @Test
    public void testRes() throws IOException {
        for (Species s : Arrays.asList(Species.Human,
                Species.Mouse)) {
            for (Gene g : Arrays.asList(
                    Gene.TRA,
                    Gene.TRB,
                    Gene.IGH,
                    Gene.IGK,
                    Gene.IGL,
                    //Gene.TRG, todo: fix mouse no TRGJ
                    Gene.TRD
            )) {
                System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(s, g));
            }
        }
    }
}