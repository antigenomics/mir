package com.antigenomics.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Assert;
import org.junit.Test;

import static com.antigenomics.mir.clonotype.ClonotypeHelper.*;

public class ClonotypeHelperTest {
    @Test
    public void clonotypeTest() {
        System.out.println(clonotypeFrom("TGTTGCGCTCCTGTCTCT"));

        var c = clonotypeFrom("TGTTGTCCGCTCGTCTCT", "TRBV1", "TRBJ1");
        System.out.println(c);

        Assert.assertEquals(new AminoAcidSequence("CCPLVS"), c.getCdr3Aa());
    }

    @Test
    public void clonotypeTableTest() {
        var sample = clonotypeTableFrom(clonotypeFrom("TGTTGCGCTCCTGTCTCT"),
                clonotypeFrom("TGTTGTCCGCTCGTCTCT", "TRBV1", "TRBJ1")
        );
        System.out.println(sample);
        Assert.assertEquals(2, sample.size());
    }

    @Test
    public void mockTranslateTest() {
        var c = clonotypeFromAa("CASS");
        System.out.println(c);
        Assert.assertEquals(new NucleotideSequence("TGTGCTTCTTCT"), c.getCdr3Nt());
    }
}