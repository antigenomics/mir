package com.milaboratory.mir.clonotype;

import org.junit.Test;

import static com.milaboratory.mir.clonotype.ClonotypeHelper.*;
import static org.junit.Assert.*;

public class ClonotypeHelperTest {
    @Test
    public void clonotypeTest() {
        System.out.println(clonotypeFrom("TGTTGCGCTCCTGTCTCT"));

        System.out.println(clonotypeFrom("TGTTGTCCGCTCGTCTCT", "TRBV1", "TRBJ1"));
    }

    @Test
    public void clonotypeTableTest() {
        System.out.println(
                clonotypeTableFrom(clonotypeFrom("TGTTGCGCTCCTGTCTCT"),
                        clonotypeFrom("TGTTGTCCGCTCGTCTCT", "TRBV1", "TRBJ1")
                ));
    }
}