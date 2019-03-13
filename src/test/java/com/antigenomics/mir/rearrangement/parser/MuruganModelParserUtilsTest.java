package com.antigenomics.mir.rearrangement.parser;

import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MuruganModelParserUtilsTest {
    @Test
    public void getResourceStreamTest() throws IOException {
        getResourceStreamTest(Species.Human, Gene.TRB);
        getResourceStreamTest(Species.Human, Gene.TRA);
        getResourceStreamTest(Species.Human, Gene.IGH);
        getResourceStreamTest(Species.Mouse, Gene.TRB);
    }

    private void getResourceStreamTest(Species species, Gene gene) throws IOException {
        MuruganModelStream res = MuruganModelParserUtils.getResourceStream(species, gene);
        Assert.assertTrue(TestUtils.testStream(res.getMarginals()));
        Assert.assertTrue(TestUtils.testStream(res.getParams()));
    }

    @Test
    public void loadFromResourceTest() {
        MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB);
        MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRA);
        MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.IGH);
        MuruganModelParserUtils.getModelFromResources(Species.Mouse, Gene.TRB);
    }

    @Test(expected = RuntimeException.class)
    public void loadFromResourceTest1() {
        MuruganModelParserUtils.getModelFromResources(Species.Mouse, Gene.TRA);
    }
}