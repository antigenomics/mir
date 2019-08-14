package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;
import org.junit.Assert;
import org.junit.Test;

public class JoiningSegmentImplTest {
    // todo
    @Test
    public void mockTest() {
        System.out.println(JoiningSegmentImpl.mock("TRBJ6"));
    }

    @Test
    public void germlineTest1() {
        var j = SegmentLibraryUtils
                .getBuiltinTcrAbLibraryBundle()
                .get(Species.Human, Gene.TRB)
                .getJ("TRBJ2-7*01");

        System.out.println(j.getGermlineSequenceNt());
        System.out.println(j.getGermlineSequenceNtWithoutCdr3());

        Assert.assertTrue(j.getGermlineSequenceNt().size() > 0);
        Assert.assertEquals(j.getGermlineSequenceNt(),
                j.getCdr3Part().concatenate(j.getGermlineSequenceNtWithoutCdr3()));
    }
}
