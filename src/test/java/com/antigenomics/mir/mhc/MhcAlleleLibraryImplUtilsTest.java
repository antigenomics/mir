package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;
import org.junit.Test;

import java.util.Arrays;

public class MhcAlleleLibraryImplUtilsTest {
    @Test
    public void loadTest() {
        for (Species species : Arrays.asList(Species.Human, Species.Mouse)) {
            for (MhcClassType mhcClassType : Arrays.asList(MhcClassType.MHCI, MhcClassType.MHCII)) {
                System.out.println(MhcAlleleLibraryUtils.getLibraryFromResources(species, mhcClassType));
            }
        }
    }
}