package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.stream.Collectors;

public class SegmentMarkupRealignerNtTest {
    @Test
    public void realignTest() throws IOException {
        // N.B. will not work for some other segments due to trimming of AAs at 5'/3'

        var segmId = "TRBV7-1*01";
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        var segment = lib.getV(segmId);

        var realigner = new SegmentMarkupRealignerNt<>(
                lib.getAllVMajor().stream().filter(x -> x != segment).collect(Collectors.toList()),
                new SimpleExhaustiveMapperFactory<>(AffineGapAlignmentScoring.getNucleotideBLASTScoring())
        );

        var observed = realigner.recomputeMarkup(segment.getGermlineSequenceNt());
        Assert.assertTrue(observed.isPresent());
        Assert.assertEquals(segment.getRegionMarkupNt(), observed.get().getMarkup());
    }
}
