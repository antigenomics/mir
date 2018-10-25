package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.mir.structure.pdb.Structure;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PeptideMhcComplexMapperTest {

    @Test
    public void test() {
        var struct = TestStructureCache.get("1ao7");

        var mapper = new PeptideMhcComplexMapper(
                new SimpleExhaustiveMapperFactory<>(
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );

        var resOpt = mapper.map(struct);

        Assert.assertTrue(resOpt.isPresent());

        var res = resOpt.get();

        Assert.assertEquals(Species.Human, res.getSpecies());
        var pept = res.getPeptideMhcComplex().getPeptideChain();
        Assert.assertEquals('C', pept.getStructureChain().getChainIdentifier());
        Assert.assertEquals(new AminoAcidSequence("LLFGYPVYV"), pept.getSequence());
    }
}