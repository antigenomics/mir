package com.antigenomics.mir.probability.parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static java.util.Collections.singletonList;

public class PlainTextHierarchicalModelTest {
    private static final String toyModel = "variables\tvalues\tprobability\n" +
            "A|B\ta1|b1\t0.5\n" +
            "A|B\ta2|b1\t0.5\n" +
            "A|B\ta2|b2\t1.0\n" +
            "A|B\ta1|b3\t1.0\n" +
            "A|B\ta2|b3\t0.0\n" +
            "B\tb1\t0.25\n" +
            "B\tb2\t0.25\n" +
            "B\tb3\t0.25\n" +
            "B\tb4\t0.25\n";

    @Test(expected = NullPointerException.class)
    public void unfortifiedTest1() throws IOException {
        var mdl = PlainTextHierarchicalModel.fromString(toyModel);
        mdl.getProbability(singletonList("a1"), singletonList("b2"), "A|B");
    }

    @Test(expected = NullPointerException.class)
    public void unfortifiedTest2() throws IOException {
        var mdl = PlainTextHierarchicalModel.fromString(toyModel);
        mdl.getProbability(singletonList("a1"), singletonList("b4"), "A|B");
    }

    @Test
    public void fortifyTest() throws IOException {
        var mdl = PlainTextHierarchicalModel.fromString(toyModel);
        var mdlFortif = mdl.fortify();
        Assert.assertEquals(0d,
                mdlFortif.getProbability(singletonList("a1"), singletonList("b2"), "A|B"), 0d);

        Assert.assertEquals(0.5d,
                mdlFortif.getProbability(singletonList("a1"), singletonList("b4"), "A|B"), 0d);
    }

    @Test
    public void ioTest() throws IOException {
        var mdl = PlainTextHierarchicalModel.fromString(toyModel);
        var orig = PlainTextHierarchicalModel.toString(mdl);
        Assert.assertEquals(orig, PlainTextHierarchicalModel.toString(PlainTextHierarchicalModel.fromString(orig)));
    }
}