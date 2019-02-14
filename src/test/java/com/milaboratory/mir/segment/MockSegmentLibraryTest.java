package com.milaboratory.mir.segment;

import org.junit.Test;

import static org.junit.Assert.*;

public class MockSegmentLibraryTest {
    @Test
    public void test() {
        var lib = new MockSegmentLibrary(Species.Human, Gene.TRB);

        lib.getV("TRBV1");
        lib.getJ("TRBJ1");
        lib.getD("TRBD1");

        System.out.println(lib);

        lib.getC("TRBC1");

        System.out.println(lib);
    }
}