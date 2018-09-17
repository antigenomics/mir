package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.mappers.markup.DummyRegion;
import com.milaboratory.mir.mappers.markup.ReceptorMarkupRealignerAa;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.milaboratory.mir.structure.AntigenReceptorRegionType.CDR3;

public class PdbChainTest {
    private final Structure struct = PdbParserUtils
            .parseStructure("1ao7_al", TestUtils.streamFrom("structures/1ao7_al.pdb"));

    public PdbChainTest() throws IOException {
    }

    @Test
    public void getRegionTest1() throws IOException {
        var region = struct.getChain('A')
                .getRegion(new SequenceRegion<>(DummyRegion.R1,
                        new AminoAcidSequence("GSHSMRY"),
                        0, 7));
        System.out.println(region);
        Assert.assertEquals(new AminoAcidSequence("GSHSMRY"), region.getSequence());
    }

    @Test
    public void getRegionTest2(){
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);

        var realigner = new ReceptorMarkupRealignerAa(
                lib, new SimpleExhaustiveMapperFactory<>(
                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
        ), true
        );

        var res = realigner.recomputeMarkup(struct.getChain('E').getSequence());

        System.out.println(struct.getChain('E').getSequence());
        System.out.println(res);

        Assert.assertTrue(res.isPresent());
        var resUnboxed = res.get();
        System.out.println(resUnboxed);

        Assert.assertEquals(
                new AminoAcidSequence("CASRPGLAGGRPEQYF"),
                struct.getChain('A').getRegion(resUnboxed.getRegion(CDR3)).getSequence());
    }
}