package com.milaboratory.mir.clonotype.parser;


import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MixcrClonotypeParserTest {
    @Test
    public void parseTest() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var header = "count\tfreq\tcdr3nt\tcdr3aa\tv\td\tj\tVEnd\tDStart\tDEnd\tJStart";
        var parser = new MixcrClonotypeParser(header.split("\t"), lib, true);
        var line = "207\t0.07587976539589443\tTGTGCTTATCGAGAGGGGGGCGCCAGACTCATGTTT\tCAYREGGARLMF\tTRAV38-2DV8\tTRDD3\tTRAJ31\t8\t14\t18\t21";
        var result = parser.parse(line.split("\t"));
        assertEquals(207, result.getCount());
        assertEquals(new AminoAcidSequence("CAYREGGARLMF"), result.getClonotype().getCdr3Aa());
    }

}