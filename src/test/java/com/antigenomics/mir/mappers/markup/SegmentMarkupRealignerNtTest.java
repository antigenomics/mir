package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
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
