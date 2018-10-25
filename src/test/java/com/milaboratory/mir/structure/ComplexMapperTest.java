package com.milaboratory.mir.structure;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.pdb.Structure;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ComplexMapperTest {

    @Test
    public void test() throws IOException {
        Structure struct = PdbParserUtils
                .parseStructure("1ao7_al", TestUtils.streamFrom("structures/1ao7_al.pdb"));

        var mapper = new ComplexMapper(
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