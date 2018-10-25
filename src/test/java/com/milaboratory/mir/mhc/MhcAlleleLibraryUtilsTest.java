package com.milaboratory.mir.mhc;

import com.milaboratory.mir.segment.Species;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MhcAlleleLibraryUtilsTest {
    @Test
    public void loadTest() {
        for (Species species : Arrays.asList(Species.Human, Species.Mouse)) {
            for (MhcClassType mhcClassType : Arrays.asList(MhcClassType.MHCI, MhcClassType.MHCII)) {
                System.out.println(MhcAlleleLibraryUtils.load(species, mhcClassType));
            }
        }
    }
}