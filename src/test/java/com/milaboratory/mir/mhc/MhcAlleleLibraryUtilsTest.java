package com.milaboratory.mir.mhc;

import com.milaboratory.mir.segment.Species;
import org.junit.Test;

import static org.junit.Assert.*;

public class MhcAlleleLibraryUtilsTest {
    @Test
    public void loadTest() {
        System.out.println(MhcAlleleLibraryUtils.load(Species.Human, MhcClassType.MHCI));
        System.out.println(MhcAlleleLibraryUtils.load(Species.Human, MhcClassType.MHCII));
        System.out.println(MhcAlleleLibraryUtils.load(Species.Mouse, MhcClassType.MHCI));
        System.out.println(MhcAlleleLibraryUtils.load(Species.Mouse, MhcClassType.MHCII));
    }
}