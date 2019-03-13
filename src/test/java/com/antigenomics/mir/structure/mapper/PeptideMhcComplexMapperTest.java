package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.structure.TestStructureCache;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.structure.TcrPeptideMhcComplex;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class PeptideMhcComplexMapperTest {

    @Test
    public void test1() {
        var resOpt = map("1ao7");

        Assert.assertTrue(resOpt.isPresent());

        var res = resOpt.get();

        Assert.assertEquals(Species.Human, res.getSpecies());
        var pept = res.getPeptideMhcComplex().getPeptideChain();
        Assert.assertEquals('C', pept.getStructureChain().getChainIdentifier());
        Assert.assertEquals(new AminoAcidSequence("LLFGYPVYV"), pept.getSequence());
    }

    @Test
    public void test2() {
        var resOpt = map("2oi9");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test3() {
        var resOpt = map("2pxy");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test4() {
        var resOpt = map("4ozh");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test5() {
        var resOpt = map("3mbe");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test6() {
        var resOpt = map("3pl6");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test7() {
        var resOpt = map("4grl");

        Assert.assertTrue(resOpt.isPresent());
    }

    public static Optional<TcrPeptideMhcComplex> map(String internalId) {
        var struct = TestStructureCache.get(internalId);

        var mapper = PeptideMhcComplexMapper.DEFAULT;

        return mapper.map(struct);
    }
}