package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.alignment.*;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SequenceRegionMarkupUtilsTest {
    @Test
    public void realignTest() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        VariableSegment v1 = lib.getV("TRBV5-3*01"),
                v2 = lib.getV("TRBV5-4*01");

        var alignment = Aligner.alignLocal(
                LinearGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62),
                v1.getGermlineSequenceAa(),
                v2.getGermlineSequenceAa());

        var expectedV2Markup = v2.getRegionMarkupAa();
        var observedV2Markup = SequenceRegionMarkupUtils.realign(
                (PrecomputedSequenceRegionMarkup) v1.getRegionMarkupAa(),
                v2.getRegionMarkupAa().getFullSequence(),
                alignment);

        Assert.assertEquals(expectedV2Markup, observedV2Markup);
    }
}
