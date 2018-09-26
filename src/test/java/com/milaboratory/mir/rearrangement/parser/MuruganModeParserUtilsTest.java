package com.milaboratory.mir.rearrangement.parser;

import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MuruganModeParserUtilsTest {
    @Test
    public void getResourceStreamTest() throws IOException {
        getResourceStreamTest(Species.Human, Gene.TRB);
        getResourceStreamTest(Species.Human, Gene.TRA);
        getResourceStreamTest(Species.Human, Gene.IGH);
        getResourceStreamTest(Species.Mouse, Gene.TRB);
    }

    private void getResourceStreamTest(Species species, Gene gene) throws IOException {
        MuruganModelStream res = MuruganModeParserUtils.getResourceStream(species, gene);
        Assert.assertTrue(TestUtils.testStream(res.getMarginals()));
        Assert.assertTrue(TestUtils.testStream(res.getParams()));
    }

    @Test
    public void loadFromResourceTest() throws IOException {
        MuruganModeParserUtils.getModelFromResources(Species.Human, Gene.TRB);
        MuruganModeParserUtils.getModelFromResources(Species.Human, Gene.TRA);
        MuruganModeParserUtils.getModelFromResources(Species.Human, Gene.IGH);
        MuruganModeParserUtils.getModelFromResources(Species.Mouse, Gene.TRB);
    }

    @Test(expected = RuntimeException.class)
    public void loadFromResourceTest1() throws IOException {
        MuruganModeParserUtils.getModelFromResources(Species.Mouse, Gene.TRA);
    }
}