package com.milaboratory.mir.rearrangement.probability;

import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class HierarchicalModelFormulaTest {
    @Test
    public void createTest1() {
        Assert.assertEquals("P(J|V)P(V)P(D|V,J)",
                HierarchicalModelFormula.fromString("P(D|V,J) P(J |V) P( V )").toString());

        Assert.assertEquals(new HashSet<>(Collections.singletonList("V")),
                HierarchicalModelFormula.fromString("P(D|V,J) P(J |V) P( V )").getParentVariables("J"));
    }

}