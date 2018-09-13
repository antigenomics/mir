package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.alignment.LinearGapAlignmentScoring;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PrecomputedSequenceRegionMarkupTest {
    @Test
    public void realignTest() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        VariableSegment target = lib.getV("TRBV5-3*01"),
                query = lib.getV("TRBV5-4*01");

        var alignment = Aligner.alignLocal(
                LinearGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62),
                target.getRegionMarkupAa().fullSequence,
                query.getRegionMarkupAa().fullSequence);

        var expectedMarkup = query.getRegionMarkupAa();
        var observedMarkup = target.getRegionMarkupAa().realign(query.getRegionMarkupAa().fullSequence, alignment);

        Assert.assertEquals(expectedMarkup, observedMarkup);
    }

    @Test
    public void realignTest2() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        JoiningSegment target = lib.getJ("TRBJ2-1*01"),
                query = lib.getJ("TRBJ2-2*01");

        var alignment = Aligner.alignLocal(
                LinearGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62),
                target.getRegionMarkupAa().fullSequence,
                query.getRegionMarkupAa().fullSequence);

        var expectedMarkup = query.getRegionMarkupAa();
        var observedMarkup = target.getRegionMarkupAa().realign(query.getRegionMarkupAa().fullSequence, alignment);

        Assert.assertEquals(expectedMarkup.getRegion(AntigenReceptorRegionType.CDR3).getEnd(),
                observedMarkup.getRegion(AntigenReceptorRegionType.CDR3).getEnd());
    }
}