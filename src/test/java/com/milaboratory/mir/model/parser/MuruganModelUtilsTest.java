package com.milaboratory.mir.model.parser;

import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MuruganModelUtilsTest {
    @Test
    public void getResourceStreamTest() throws IOException {
        getResourceStreamTest(Species.Human, Gene.TRB);
        getResourceStreamTest(Species.Human, Gene.TRA);
        getResourceStreamTest(Species.Human, Gene.IGH);
        getResourceStreamTest(Species.Mouse, Gene.TRB);
    }

    private void getResourceStreamTest(Species species, Gene gene) throws IOException {
        MuruganModelStream res = MuruganModelUtils.getResourceStream(species, gene);
        Assert.assertTrue(TestUtils.testStream(res.getMarginals()));
        Assert.assertTrue(TestUtils.testStream(res.getParams()));
    }

    @Test
    public void loadFromResourceTest() throws IOException {
        var mdl = MuruganModelUtils.getModelFromResources(Species.Human, Gene.TRB);
        MuruganModelUtils.getModelFromResources(Species.Human, Gene.TRA);
        MuruganModelUtils.getModelFromResources(Species.Human, Gene.IGH);
        MuruganModelUtils.getModelFromResources(Species.Mouse, Gene.TRB);
    }
}