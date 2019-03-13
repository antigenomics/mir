package com.antigenomics.mir.structure.pdb;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.mappers.markup.DummyRegion;
import com.antigenomics.mir.mappers.markup.ReceptorMarkupRealignerAa;
import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.segment.parser.MigecSegmentLibraryUtils;
import com.antigenomics.mir.structure.TestStructureCache;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.antigenomics.mir.structure.AntigenReceptorRegionType.CDR3;

public class ChainTest {
    public ChainTest() throws IOException {
    }

    @Test
    public void getRegionTest1() {
        var struct = TestStructureCache.get("1ao7");
        var region = struct.getChain('A')
                .extractRegion(new SequenceRegion<>(DummyRegion.R1,
                        new AminoAcidSequence("GSHSMRY"),
                        0, 7));
        System.out.println(region);
        Assert.assertEquals(new AminoAcidSequence("GSHSMRY"), region.getSequence());
    }

    @Test
    public void getRegionTest2() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        var struct = TestStructureCache.get("1ao7");

        var realigner = new ReceptorMarkupRealignerAa(
                lib, new SimpleExhaustiveMapperFactory<>(
                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
        ), true
        );

        var chain = struct.getChain('E');
        var res = realigner.recomputeMarkup(chain.getSequence());

        Assert.assertTrue(res.isPresent());
        var resUnboxed = res.get().getMarkup();
        System.out.println(resUnboxed);

        Assert.assertEquals(
                new AminoAcidSequence("CASRPGLAGGRPEQYF"),
                chain.extractRegion(resUnboxed.getRegion(CDR3)).getSequence());
    }
}