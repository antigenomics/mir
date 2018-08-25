package com.milaboratory.mir.rearrangement.probability;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class ProbabilisticModelFormulaTest {
    @Test
    public void createTest1() {
        Assert.assertEquals("P(J|V)P(V)P(D|V,J)",
                ProbabilisticModelFormula.fromString("P(D|V,J) P(J |V) P( V )").toString());

        Assert.assertEquals(new HashSet<>(Collections.singletonList("V")),
                ProbabilisticModelFormula.fromString("P(D|V,J) P(J |V) P( V )").getParentVariables("J"));
    }

}