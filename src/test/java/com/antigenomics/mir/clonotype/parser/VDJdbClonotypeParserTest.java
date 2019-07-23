package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.segment.parser.MigecSegmentLibraryUtils;
import com.milaboratory.core.sequence.AminoAcidSequence;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VDJdbClonotypeParserTest {

    @Test
    public void parseTest() throws IOException {
        var lib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var header = "complex.id\tgene\tcdr3\tv.segm\tj.segm\tspecies\tmhc.a\tmhc.b\tmhc.class\tantigen.epitope\tantigen.gene\tantigen.species\treference.id\tmethod\tmeta\tcdr3fix\tvdjdb.score\tweb.method\tweb.method.seq\tweb.cdr3fix.nc\tweb.cdr3fix.unmp";
        var parser = new VDJdbClonotypeParser(header.split("\t"), lib, true);
        var line = "0\tTRA\tCAVTDDKIIF\tTRAV12-2*01\tTRAJ30*01\tHomoSapiens\tHLA-A*02\tB2M\tMHCI\tLLWNGPMAV\tNS4B\tYellowFeverVirus\tPMID:28975614\t{\"frequency\": \"6/56\", \"identification\": \"tetramer-sort\", \"sequencing\": \"sanger\", \"singlecell\": \"\", \"verification\": \"\"}\t{\"cell.subset\": \"CD8\", \"clone.id\": \"\", \"donor.MHC\": \"\", \"donor.MHC.method\": \"\", \"epitope.id\": \"\", \"replica.id\": \"\", \"samples.found\": 1, \"structure.id\": \"\", \"studies.found\": 1, \"study.id\": \"\", \"subject.cohort\": \"\", \"subject.id\": \"\", \"tissue\": \"\"}\t{\"cdr3\": \"CAVTDDKIIF\", \"cdr3_old\": \"CAVTDDKIIF\", \"fixNeeded\": false, \"good\": true, \"jCanonical\": true, \"jFixType\": \"NoFixNeeded\", \"jId\": \"TRAJ30*01\", \"jStart\": 4, \"vCanonical\": true, \"vEnd\": 3, \"vFixType\": \"NoFixNeeded\", \"vId\": \"TRAV12-2*01\"}\t1\tsort\tsanger\tno\tno";
        var result = parser.parse(line.split("\t"));

        var clonotype = result.getClonotype();
        assertEquals("TRAV12-2*01", clonotype.getVariableSegmentCalls().get(0).getSegment().getId());
        assertEquals("TRAJ30*01", clonotype.getJoiningSegmentCalls().get(0).getSegment().getId());
        assertEquals(new AminoAcidSequence("CAVTDDKIIF"), clonotype.getCdr3Aa());

        assertEquals(4, clonotype.getJunctionMarkup().getJStart());
        assertEquals(3, clonotype.getJunctionMarkup().getVEnd());

        assertTrue(!clonotype.getAnnotations().isEmpty());

        System.out.println(clonotype);
    }

}
