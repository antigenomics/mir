package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.rearrangement.ClonotypeWithReadImpl;
import com.antigenomics.mir.clonotype.rearrangement.JunctionMarkup;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Test;

import static org.junit.Assert.*;

public class MixcrClonotypeParserTest {
    @Test
    public void parseTestEverythingOk() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        var header = "Clone ID\tClone count\tClone fraction\tClonal sequence(s)\tClonal sequence quality(s)\tAll V hits\tAll D hits\tAll J hits\tAll C hits\tAll V alignments\tAll D alignments\tAll J alignments\tAll C alignments\tN. Seq. FR1\tMin. qual. FR1\tN. Seq. CDR1\tMin. qual. CDR1\tN. Seq. FR2\tMin. qual. FR2\tN. Seq. CDR2\tMin. qual. CDR2\tN. Seq. FR3\tMin. qual. FR3\tN. Seq. CDR3\tMin. qual. CDR3\tN. Seq. FR4\tMin. qual. FR4\tAA. Seq. FR1\tAA. Seq. CDR1\tAA. Seq. FR2\tAA. Seq. CDR2\tAA. Seq. FR3\tAA. Seq. CDR3\tAA. Seq. FR4\tRef. points";
        var parser = new MixcrClonotypeParser(header.split("\t"), lib, true);
        var line = "0\t1153\t0.05269893505187623\tGAGGTGCAGCTGGTGGAGTCTGGGGGAGGCTTGGTCCAGCCTGGGGGGTCCCTGAGACTCTCCTGTGCAGCCTCTGGATTCACCGTCATTAGCAACTACATGAGCTGGGTCCGCCAGGCTCCAGGAAAGGGGCTGGAGTGGGTCTCACTTATTTATAGCGGTGGTGGCACATACTACGCAGATTCCGTGAAGGGCAGATTCACCATCTCCAGAGACAATTCCAAGAATACGCTCTATCTTCAAATGAACAGCCTGAGAGCCGAGGACACGGCTGTGTATTACTGTGCGAGAGTGGAAAACTCCTTCTACTACTACATGGACGTCTGGGGCAAAGGGACCACGGTCACCGTCTCCTCAG\tHHIHHHHHHHHIHHHHGHGHGIIHHIFIHHHGHHHHGHHHHHIIHHIHGGGGHHEGEGFFEHHGHEGGGHHGFFEIHDGGFEHHHEGEHGDGGGGGFFGEGG?HFEHIHEHHHHHFIIFBHHGIIHIHIIIIEGIIBHCHIHGFGEHFHHHHIHHGHHGHHEHHFHHGIGHHHHHHHHHHHHHHHIHHIIIIHIIIHIHIIIIIIHIHIIIIHIIHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII\tIGHV3-66*00(3863.5),IGHV3-53*00(3778.4)\tIGHD1-1*00(50),IGHD1-20*00(50),IGHD1-7*00(50)\tIGHJ6*00(542)\tIGHM*00(154.5)\t157|449|470|0|292|SA180GSA190GSG234ASG245TSG282ASG304CST316GSA322GSC339TSC352ASC384TSG390CST417C|2543.0;157|449|470|0|292|SA180GSA190GSG234ASG245TSG282ASG304CSA322GSC339TSC352ASC384TSG390CSC429T|2572.0\t25|30|51|292|297||50.0;25|30|51|292|297||50.0;25|30|51|292|297||50.0\t23|83|83|298|358|SA26CSA29T|542.0\t\tGAGGTGCAGCTGGTGGAGTCTGGGGGAGGCTTGGTCCAGCCTGGGGGGTCCCTGAGACTCTCCTGTGCAGCCTCT\t36\tGGATTCACCGTCATTAGCAACTAC\t35\tATGAGCTGGGTCCGCCAGGCTCCAGGAAAGGGGCTGGAGTGGGTCTCACTT\t30\tATTTATAGCGGTGGTGGCACA\t36\tTACTACGCAGATTCCGTGAAGGGCAGATTCACCATCTCCAGAGACAATTCCAAGAATACGCTCTATCTTCAAATGAACAGCCTGAGAGCCGAGGACACGGCTGTGTATTAC\t39\tTGTGCGAGAGTGGAAAACTCCTTCTACTACTACATGGACGTCTGG\t40\tGGCAAAGGGACCACGGTCACCGTCTCCTCAG\t40\tEVQLVESGGGLVQPGGSLRLSCAAS\tGFTVISNY\tMSWVRQAPGKGLEWVSL\tIYSGGGT\tYYADSVKGRFTISRDNSKNTLYLQMNSLRAEDTAVYY\tCARVENSFYYYMDVW\tGKGTTVTVSS\t::::0:75:99:150:171:282::292:292:::297:298::327:358::\n";
        var result = parser.parse(line.split("\t"));
        assertEquals(1153, result.getCount());
        ClonotypeWithReadImpl clonotype = result.getClonotype();
        assertEquals(new AminoAcidSequence("CARVENSFYYYMDVW"), clonotype.getCdr3Aa());
        JunctionMarkup junctionMarkup = clonotype.getJunctionMarkup();
        assertEquals(297-282, junctionMarkup.getDEnd());
        assertEquals(292-282, junctionMarkup.getDStart());
        assertEquals(298-282, junctionMarkup.getJStart());
        assertEquals(292-282, junctionMarkup.getVEnd());
        assertEquals("IGHV3-66*01", clonotype.getVariableSegmentCalls().get(0).getSegment().getId());
        assertEquals("IGHD1-1*01", clonotype.getDiversitySegmentCalls().get(0).getSegment().getId());
        assertEquals("IGHJ6*01", clonotype.getJoiningSegmentCalls().get(0).getSegment().getId());
        // TODO: isotypes
        //assertEquals("IGHM*01", result.getClonotype().getConstantSegmentCalls().get(0).getSegment().getId());
        assertEquals(3778.4, clonotype.getVariableSegmentCalls().get(1).getWeight(), 0.001);
        assertEquals(13, clonotype.getVMutations().size());
        assertEquals(2, clonotype.getJMutations().size());
        assertEquals(new NucleotideSequence("GAGGTGCAGCTGGTGGAGTCTGGGGGAGGCTTGGTCCAGCCTGGGGGGTCCCTGAGACTCTCCTGTGCAGCCTCT"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.FR1).getSequence());
        assertEquals(new NucleotideSequence("GGATTCACCGTCATTAGCAACTAC"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.CDR1).getSequence());
        assertEquals(new NucleotideSequence("ATGAGCTGGGTCCGCCAGGCTCCAGGAAAGGGGCTGGAGTGGGTCTCACTT"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.FR2).getSequence());
        assertEquals(new NucleotideSequence("ATTTATAGCGGTGGTGGCACA"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.CDR2).getSequence());
        assertEquals(new NucleotideSequence("TACTACGCAGATTCCGTGAAGGGCAGATTCACCATCTCCAGAGACAATTCCAAGAATACGCTCTATCTTCAAATGAACAGCCTGAGAGCCGAGGACACGGCTGTGTATTAC"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.FR3).getSequence());
        assertEquals(new NucleotideSequence("GGCAAAGGGACCACGGTCACCGTCTCCTCAG"),
                clonotype.getMarkup().getRegion(AntigenReceptorRegionType.FR4).getSequence());
    }

    @Test
    public void parseTestNoAminoacidSequence() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        var header = "Clone ID\tClone count\tClone fraction\tClonal sequence(s)\tClonal sequence quality(s)\tAll V hits\tAll D hits\tAll J hits\tAll C hits\tAll V alignments\tAll D alignments\tAll J alignments\tAll C alignments\tN. Seq. FR1\tMin. qual. FR1\tN. Seq. CDR1\tMin. qual. CDR1\tN. Seq. FR2\tMin. qual. FR2\tN. Seq. CDR2\tMin. qual. CDR2\tN. Seq. FR3\tMin. qual. FR3\tN. Seq. CDR3\tMin. qual. CDR3\tN. Seq. FR4\tMin. qual. FR4\tAA. Seq. FR1\tAA. Seq. CDR1\tAA. Seq. FR2\tAA. Seq. CDR2\tAA. Seq. FR3\tAA. Seq. FR4\tRef. points";
        var parser = new MixcrClonotypeParser(header.split("\t"), lib, true);
        var line = "0\t1153\t0.05269893505187623\tGAGGTGCAGCTGGTGGAGTCTGGGGGAGGCTTGGTCCAGCCTGGGGGGTCCCTGAGACTCTCCTGTGCAGCCTCTGGATTCACCGTCATTAGCAACTACATGAGCTGGGTCCGCCAGGCTCCAGGAAAGGGGCTGGAGTGGGTCTCACTTATTTATAGCGGTGGTGGCACATACTACGCAGATTCCGTGAAGGGCAGATTCACCATCTCCAGAGACAATTCCAAGAATACGCTCTATCTTCAAATGAACAGCCTGAGAGCCGAGGACACGGCTGTGTATTACTGTGCGAGAGTGGAAAACTCCTTCTACTACTACATGGACGTCTGGGGCAAAGGGACCACGGTCACCGTCTCCTCAG\tHHIHHHHHHHHIHHHHGHGHGIIHHIFIHHHGHHHHGHHHHHIIHHIHGGGGHHEGEGFFEHHGHEGGGHHGFFEIHDGGFEHHHEGEHGDGGGGGFFGEGG?HFEHIHEHHHHHFIIFBHHGIIHIHIIIIEGIIBHCHIHGFGEHFHHHHIHHGHHGHHEHHFHHGIGHHHHHHHHHHHHHHHIHHIIIIHIIIHIHIIIIIIHIHIIIIHIIHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII\tIGHV3-66*00(3863.5),IGHV3-53*00(3778.4)\tIGHD1-1*00(50),IGHD1-20*00(50),IGHD1-7*00(50)\tIGHJ6*00(542)\tIGHM*00(154.5)\t157|449|470|0|292|SA180GSA190GSG234ASG245TSG282ASG304CST316GSA322GSC339TSC352ASC384TSG390CST417C|2543.0;157|449|470|0|292|SA180GSA190GSG234ASG245TSG282ASG304CSA322GSC339TSC352ASC384TSG390CSC429T|2572.0\t25|30|51|292|297||50.0;25|30|51|292|297||50.0;25|30|51|292|297||50.0\t23|83|83|298|358|SA26CSA29T|542.0\t\tGAGGTGCAGCTGGTGGAGTCTGGGGGAGGCTTGGTCCAGCCTGGGGGGTCCCTGAGACTCTCCTGTGCAGCCTCT\t36\tGGATTCACCGTCATTAGCAACTAC\t35\tATGAGCTGGGTCCGCCAGGCTCCAGGAAAGGGGCTGGAGTGGGTCTCACTT\t30\tATTTATAGCGGTGGTGGCACA\t36\tTACTACGCAGATTCCGTGAAGGGCAGATTCACCATCTCCAGAGACAATTCCAAGAATACGCTCTATCTTCAAATGAACAGCCTGAGAGCCGAGGACACGGCTGTGTATTAC\t39\tTGTGCGAGAGTGGAAAACTCCTTCTACTACTACATGGACGTCTGG\t40\tGGCAAAGGGACCACGGTCACCGTCTCCTCAG\t40\tEVQLVESGGGLVQPGGSLRLSCAAS\tGFTVISNY\tMSWVRQAPGKGLEWVSL\tIYSGGGT\tYYADSVKGRFTISRDNSKNTLYLQMNSLRAEDTAVYY\tGKGTTVTVSS\t::::0:75:99:150:171:282::292:292:::297:298::327:358::\n";
        var result = parser.parse(line.split("\t"));
        assertEquals(new AminoAcidSequence("CARVENSFYYYMDVW"), result.getClonotype().getCdr3Aa());
    }

    @Test
    public void parseTestTrimmings() {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        var header = "cloneId\tcloneCount\tcloneFraction\tclonalSequence\tclonalSequenceQuality\tallVHitsWithScore\tallDHitsWithScore\tallJHitsWithScore\tallCHitsWithScore\tallVAlignments\tallDAlignments\tallJAlignments\tallCAlignments\tnSeqFR1\tminQualFR1\tnSeqCDR1\tminQualCDR1\tnSeqFR2\tminQualFR2\tnSeqCDR2\tminQualCDR2\tnSeqFR3\tminQualFR3\tnSeqCDR3\tminQualCDR3\tnSeqFR4\tminQualFR4\taaSeqFR1\taaSeqCDR1\taaSeqFR2\taaSeqCDR2\taaSeqFR3\taaSeqCDR3\taaSeqFR4\trefPoints";
        var parser = new MixcrClonotypeParser(header.split("\t"), lib, true);
        var line = "0\t43\t0.5733333333333334\tTGTGCCAGCAGCAAAGCTCGCTGGGACTTCACAGCCAACGTCCTGACTTTC\tIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII\tTRBV21-1*00(369.4)\tTRBD2*00(30)\tTRBJ2-6*00(230)\tTRBC2*00(70.3)\t277|294|314|0|17||85.0\t16|22|48|22|28||30.0\t27|45|73|33|51||90.0\t\t\t\t\t\t\t\t\t\t\t\tTGTGCCAGCAGCAAAGCTCGCTGGGACTTCACAGCCAACGTCCTGACTTTC\t40\t\t\t\t\t\t\t\tCASSKARWDFTANVLTF\t\t:::::::::0:0:17:22:0:-10:28:33:-7:51:::\n";
        var result = parser.parse(line.split("\t"));
        assertEquals(new AminoAcidSequence("CASSKARWDFTANVLTF"), result.getClonotype().getCdr3Aa());
        assertEquals(0, result.getClonotype().getSegmentTrimming().getVTrimming());
        assertEquals(7, result.getClonotype().getSegmentTrimming().getJTrimming());
        assertEquals(0, result.getClonotype().getSegmentTrimming().getDTrimming5());
        assertEquals(10, result.getClonotype().getSegmentTrimming().getDTrimming3());
    }
}