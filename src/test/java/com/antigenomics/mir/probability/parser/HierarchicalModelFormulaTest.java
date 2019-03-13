package com.antigenomics.mir.probability.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class HierarchicalModelFormulaTest {
    @Test
    public void test1() {
        Assert.assertEquals("P(J|V)P(V)P(D|V,J)",
                HierarchicalModelFormula.fromString("P(D|V,J) P(J |V) P( V )").toString());

        Assert.assertArrayEquals(new String[]{"V"},
                HierarchicalModelFormula.fromString("P(D|V,J) P(J |V) P( V )").getParentVariables("J").toArray());
    }

    @Test
    public void test2() {
        var mdl = HierarchicalModelFormula.fromString("P(J|V)P(V)P(D|V,J)");
        Assert.assertEquals(
                new HashSet<>(Arrays.asList("J|V", "V", "D|V,J")),
                mdl.getBlockNames()
        );
        Assert.assertTrue(mdl.hasVariable("V"));
        Assert.assertTrue(mdl.hasVariable("D"));
        Assert.assertTrue(mdl.hasVariable("J"));
        Assert.assertEquals(Arrays.asList("V", "J"), mdl.getParentVariables("D"));
        Assert.assertEquals("J|V", mdl.findBlockName("J"));
    }
}