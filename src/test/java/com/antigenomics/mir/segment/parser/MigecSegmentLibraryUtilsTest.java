package com.antigenomics.mir.segment.parser;

import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.util.Arrays;

public class MigecSegmentLibraryUtilsTest {
    @Test
    public void testRes() {
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