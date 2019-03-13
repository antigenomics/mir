package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.TestUtils;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.antigenomics.mir.structure.pdb.parser.PdbParserUtils;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.structure.AntigenReceptorChain;
import com.antigenomics.mir.structure.pdb.Structure;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class AntigenReceptorMapperTest {

    @Test
    public void test() throws IOException {
        Structure struct = PdbParserUtils
                .parseStructure("1ao7_al", TestUtils.streamFrom("structures/1ao7_al.pdb"));

        var allVAlleles = DefaultComplexMapperLibrary.INSTANCE.getVariableSegments();
        var allJAlleles = DefaultComplexMapperLibrary.INSTANCE.getJoiningSegments();

        var mapper = new AntigenReceptorMapper(allVAlleles, allJAlleles,
                new SimpleExhaustiveMapperFactory<>(
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );

        var resOpt = mapper.map(struct.getChains());

        Assert.assertTrue(resOpt.isPresent());

        var res = resOpt.get();

        Assert.assertNotNull(res.getFirstChain());
        Assert.assertNotNull(res.getSecondChain());

        var byChainIdMap = new HashMap<Character, AntigenReceptorChain>();
        byChainIdMap.put(res.getFirstChain().getStructureChain().getChainIdentifier(),
                res.getFirstChain());
        byChainIdMap.put(res.getSecondChain().getStructureChain().getChainIdentifier(),
                res.getSecondChain());

        Assert.assertTrue(byChainIdMap.containsKey('D'));
        Assert.assertTrue(byChainIdMap.containsKey('E'));

        var tra = byChainIdMap.get('D');
        var trb = byChainIdMap.get('E');

        Assert.assertEquals("TRAV12-2*01", tra.getVariableSegment().getId());
        Assert.assertEquals("TRAJ24*01", tra.getJoiningSegment().getId());
        Assert.assertEquals("TRBV6-5*01", trb.getVariableSegment().getId());
        Assert.assertEquals("TRBJ2-7*01", trb.getJoiningSegment().getId());

        Assert.assertEquals(new AminoAcidSequence("CAVTTDSWGKLQF"),
                tra.getMarkup().getRegion(AntigenReceptorRegionType.CDR3).getSequence());
        Assert.assertEquals(new AminoAcidSequence("CASRPGLAGGRPEQYF"),
                trb.getMarkup().getRegion(AntigenReceptorRegionType.CDR3).getSequence());
    }
}