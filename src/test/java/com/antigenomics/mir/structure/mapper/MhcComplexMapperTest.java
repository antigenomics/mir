package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.mhc.MhcAlleleLibraryUtils;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.structure.MhcChain;
import com.antigenomics.mir.structure.TestStructureCache;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class MhcComplexMapperTest {
    @Test
    public void test() throws IOException {
        var struct = TestStructureCache.get("1ao7");

        var allAlleles = MhcAlleleLibraryUtils.getBuiltinMhcLibraryBundle().getAllMhcAlleles();

        var mapper = new MhcComplexMapper(allAlleles,
                new SimpleExhaustiveMapperFactory<>(
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );

        var resOpt = mapper.map(struct.getChains());

        Assert.assertTrue(resOpt.isPresent());

        var res = resOpt.get();

        Assert.assertNotNull(res.getFirstChain());
        Assert.assertNotNull(res.getSecondChain());

        var byChainIdMap = new HashMap<Character, MhcChain>();
        byChainIdMap.put(res.getFirstChain().getStructureChain().getChainIdentifier(),
                res.getFirstChain());
        byChainIdMap.put(res.getSecondChain().getStructureChain().getChainIdentifier(),
                res.getSecondChain());

        Assert.assertTrue(byChainIdMap.containsKey('A'));
        Assert.assertTrue(byChainIdMap.containsKey('B'));

        Assert.assertEquals("HLA-A2", byChainIdMap.get('A').getMhcAllele().getId());
        Assert.assertEquals("B2M", byChainIdMap.get('B').getMhcAllele().getId());
    }
}