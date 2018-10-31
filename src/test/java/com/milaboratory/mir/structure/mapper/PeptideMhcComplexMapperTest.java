package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.TcrPeptideMhcComplex;
import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.mir.structure.pdb.Structure;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
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
    public void test5() {
        var resOpt = map("3mbe");

        Assert.assertTrue(resOpt.isPresent());
    }

    @Test
    public void test4() {
        var resOpt = map("4ozh");

        Assert.assertTrue(resOpt.isPresent());
    }

    private Optional<TcrPeptideMhcComplex> map(String internalId) {
        var struct = TestStructureCache.get(internalId);

        var mapper = new PeptideMhcComplexMapper(
                new SimpleExhaustiveMapperFactory<>(
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );

        return mapper.map(struct);
    }
}