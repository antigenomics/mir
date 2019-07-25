package com.antigenomics.mir.clonotype.parser;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class VDJtoolsClonotypeParserTest {
    @Test
    public void parseTest() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var header = "count\tfreq\tcdr3nt\tcdr3aa\tv\td\tj\tVEnd\tDStart\tDEnd\tJStart";
        var parser = new VDJtoolsClonotypeParser(header.split("\t"), lib, true);
        var line = "207\t0.07587976539589443\tTGTGCTTATCGAGAGGGGGGCGCCAGACTCATGTTT\tCAYREGGARLMF\tTRAV38-2DV8\tTRDD3\tTRAJ31\t8\t14\t18\t21";
        var result = parser.parse(line.split("\t"));
        assertEquals(207, result.getCount());
        var clonotype = result.getClonotype();
        assertEquals("TRAV38-2DV8*01", clonotype.getVariableSegmentCalls().get(0).getSegment().getId());
        assertEquals("TRAJ31*01", clonotype.getJoiningSegmentCalls().get(0).getSegment().getId());
        assertEquals(new AminoAcidSequence("CAYREGGARLMF"), clonotype.getCdr3Aa());
    }
}