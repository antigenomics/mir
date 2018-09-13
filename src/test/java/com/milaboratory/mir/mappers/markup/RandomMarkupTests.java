package com.milaboratory.mir.mappers.markup;

import org.junit.Assert;
import org.junit.Test;


public class RandomMarkupTests {
    @Test
    public void createTest() {
        MarkupGenerator.randomTest(x -> { }, 10000);
    }

    @Test
    public void asPrecomputedTest1() {
        MarkupGenerator.randomTest(ArrayBasedSequenceRegionMarkup::asPrecomputed, 10000);
    }

    @Test
    public void asPrecomputedTest2() {
        MarkupGenerator.randomTest(x -> {
            Assert.assertEquals(x, x.asPrecomputed().asArrayBased());
        }, 10000);
    }
}
