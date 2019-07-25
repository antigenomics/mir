package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;
import org.junit.Test;

import java.util.Arrays;

public class MigecSegmentLibraryUtilsTest {
    @Test
    public void testRes() {
        for (Species s : Arrays.asList(Species.Human,
                Species.Mouse, Species.Monkey)) {
            for (Gene g : Arrays.asList(
                    Gene.TRA,
                    Gene.TRB,
                    Gene.IGH,
                    Gene.IGK,
                    Gene.IGL,
                    Gene.TRG,
                    Gene.TRD
            )) {
                //todo: fix mouse no TRGJ, Monkey no IGL & TRG
                if (s == Species.Mouse && g == Gene.TRG) {
                    continue;
                }
                if (s == Species.Monkey && (g == Gene.IGL | g == Gene.TRG)) {
                    continue;
                }
                //System.out.println(s + " " + g);
                //MigecSegmentLibraryUtils.getLibraryFromResources(s, g);
                System.out.println(MigecSegmentLibraryUtils.getLibraryFromResources(s, g));
            }
        }
    }
}