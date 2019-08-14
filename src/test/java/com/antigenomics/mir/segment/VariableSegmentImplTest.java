package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;
import org.junit.Assert;
import org.junit.Test;

public class VariableSegmentImplTest {


    // todo
    @Test
    public void mockTest() {
        System.out.println(VariableSegmentImpl.mock("TRBV6"));
    }

    @Test
    public void germlineTest1() {
        var v = SegmentLibraryUtils
                .getBuiltinTcrAbLibraryBundle()
                .get(Species.Human, Gene.TRB)
                .getV("TRBV7-9*01");

        System.out.println(v.getGermlineSequenceNt());
        System.out.println(v.getGermlineSequenceNtWithoutCdr3());

        Assert.assertTrue(v.getGermlineSequenceNt().size() > 0);
        Assert.assertEquals(v.getGermlineSequenceNt(),
                v.getGermlineSequenceNtWithoutCdr3().concatenate(v.getCdr3Part()));
    }
}
