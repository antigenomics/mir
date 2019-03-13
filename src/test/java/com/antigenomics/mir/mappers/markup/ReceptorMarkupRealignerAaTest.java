package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Assert;
import org.junit.Test;

import static com.antigenomics.mir.structure.AntigenReceptorRegionType.*;

public class ReceptorMarkupRealignerAaTest {
    @Test
    public void payloadTest() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);

        var seq = new AminoAcidSequence(
                "KEVEQNSGPLSVPEGAIASLNCTYSDRGSQSFFWYRQYSGKSPELIMSIYSNGDKEDGRFTAQLNKASQYVSLLIRDSQP" +
                        "SDSATYLCAVTTDSWGKLQFGAGTQVVVTPDIQNPDPAVYQLRDSKSSDKSVCLFTDFDSQTNVSQSKDSDVYITDKTVL" +
                        "DMRSMDFKSNSAVAWSNKSDFACANAFNNSIIPEDTFFPSPESS");

        var realigner = new ReceptorMarkupRealignerAa(
                lib, new SimpleExhaustiveMapperFactory<>(
                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
        ), true
        );

        var res = realigner.recomputeMarkup(seq);
        Assert.assertTrue(res.isPresent());

        var payload = res.get().getPayload();
        System.out.println(payload.toString());
        Assert.assertTrue(payload instanceof ReceptorMarkupRealigner.VariableJoiningPair);
        Assert.assertEquals("TRAV12-2*01",
                ((ReceptorMarkupRealigner.VariableJoiningPair) payload).getVariableSegment().getId());
        Assert.assertEquals("TRAJ24*01",
                ((ReceptorMarkupRealigner.VariableJoiningPair) payload).getJoiningSegment().getId());
    }

    @Test
    public void test1() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);

        var seq = new AminoAcidSequence(
                "KEVEQNSGPLSVPEGAIASLNCTYSDRGSQSFFWYRQYSGKSPELIMSIYSNGDKEDGRFTAQLNKASQYVSLLIRDSQP" +
                        "SDSATYLCAVTTDSWGKLQFGAGTQVVVTPDIQNPDPAVYQLRDSKSSDKSVCLFTDFDSQTNVSQSKDSDVYITDKTVL" +
                        "DMRSMDFKSNSAVAWSNKSDFACANAFNNSIIPEDTFFPSPESS");

        var realigner = new ReceptorMarkupRealignerAa(
                lib, new SimpleExhaustiveMapperFactory<>(
                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
        ), true
        );

        var res = realigner.recomputeMarkup(seq);
        Assert.assertTrue(res.isPresent());

        var resUnboxed = res.get().getMarkup();
        System.out.println(resUnboxed);

        Assert.assertEquals(
                new SequenceRegion<>(FR1, new AminoAcidSequence("KEVEQNSGPLSVPEGAIASLNCTYS"), 0, 25),
                resUnboxed.getRegion(FR1));
        Assert.assertEquals(
                new SequenceRegion<>(CDR1, new AminoAcidSequence("DRGSQS"), 25, 31),
                resUnboxed.getRegion(CDR1));
        Assert.assertEquals(
                new SequenceRegion<>(FR2, new AminoAcidSequence("FFWYRQYSGKSPELIMS"), 31, 48),
                resUnboxed.getRegion(FR2));
        Assert.assertEquals(
                new SequenceRegion<>(CDR2, new AminoAcidSequence("IYSNGD"), 48, 54),
                resUnboxed.getRegion(CDR2));
        Assert.assertEquals(
                new SequenceRegion<>(FR3, new AminoAcidSequence("KEDGRFTAQLNKASQYVSLLIRDSQPSDSATYL"), 54, 87),
                resUnboxed.getRegion(FR3));
        Assert.assertEquals(
                new SequenceRegion<>(CDR3, new AminoAcidSequence("CAVTTDSWGKLQF"), 87, 100),
                resUnboxed.getRegion(CDR3));
        Assert.assertEquals(
                new SequenceRegion<>(FR4, new AminoAcidSequence("GAGTQVVVTP"), 100, 110),
                resUnboxed.getRegion(FR4));
    }

    @Test
    public void test2() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);

        var seq = new AminoAcidSequence(
                "NAGVTQTPKFQVLKTGQSMTLQCAQDMNHEYMSWYRQDPGMGLRLIHYSVGAGITDQGEVPNGYNVSRSTTEDFPLRLLS" +
                        "AAPSQTSVYFCASRPGLAGGRPEQYFGPGTRLTVTEDLKNVFPPEVAVFEPSEAEISHTQKATLVCLATGFYPDHVELSW" +
                        "WVNGKEVHSGVSTDPQPLKEQPALNDSRYALSSRLRVSATFWQNPRNHFRCQVQFYGLSENDEWTQDRAKPVTQIVSAEA" +
                        "WGRAD");

        var realigner = new ReceptorMarkupRealignerAa(
                lib, new SimpleExhaustiveMapperFactory<>(
                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
        ), true
        );

        var res = realigner.recomputeMarkup(seq);
        Assert.assertTrue(res.isPresent());
        var resUnboxed = res.get().getMarkup();
        System.out.println(resUnboxed);

        Assert.assertEquals(
                new SequenceRegion<>(FR1, new AminoAcidSequence("NAGVTQTPKFQVLKTGQSMTLQCAQD"), 0, 26),
                resUnboxed.getRegion(FR1));
        Assert.assertEquals(
                new SequenceRegion<>(CDR1, new AminoAcidSequence("MNHEY"), 26, 31),
                resUnboxed.getRegion(CDR1));
        Assert.assertEquals(
                new SequenceRegion<>(FR2, new AminoAcidSequence("MSWYRQDPGMGLRLIHY"), 31, 48),
                resUnboxed.getRegion(FR2));
        Assert.assertEquals(
                new SequenceRegion<>(CDR2, new AminoAcidSequence("SVGAGI"), 48, 54),
                resUnboxed.getRegion(CDR2));
        Assert.assertEquals(
                new SequenceRegion<>(FR3, new AminoAcidSequence("TDQGEVPNGYNVSRSTTEDFPLRLLSAAPSQTSVYF"), 54, 90),
                resUnboxed.getRegion(FR3));
        Assert.assertEquals(
                new SequenceRegion<>(CDR3, new AminoAcidSequence("CASRPGLAGGRPEQYF"), 90, 106),
                resUnboxed.getRegion(CDR3));
        Assert.assertEquals(
                new SequenceRegion<>(FR4, new AminoAcidSequence("GPGTRLTVT"), 106, 115),
                resUnboxed.getRegion(FR4));
    }

//    @Test
//    public void test3() {
//        var struct = TestStructureCache.get("3vxu");
//        var realigner = new ReceptorMarkupRealignerAa(
//                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA),
//                new SimpleExhaustiveMapperFactory<>(
//                AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
//        ), true
//        );
//
//        System.out.println(struct.getChain('D').getSequence());
//        realigner.recomputeMarkup(struct.getChain('D').getSequence());
//    }
}