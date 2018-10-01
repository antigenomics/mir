package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.io.sequence.fasta.FastaRecord;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FastaSegmentLibraryUtilsTest {
    @Test
    public void fromFasta() {
        var records = Arrays.asList(
                new FastaRecord<>(-1, "TRBV00",
                        new NucleotideSequence("GACACAGCTGTTTCCCAGACTCCAAAATACCTGGTCACACAGATGGGAAACGACAAGTCCATTAAATG" +
                                "TGAACAAAATCTGGGCCATGATACTATGTATTGGTATAAACAGGACTCTAAGAAATTTCTGAAGATAATGTTTAGCTACAATA" +
                                "ACAAGGAGATCATTATAAATGAAACAGTTCCAAATCGATTCTCACCTAAATCTCCAGACAAAGCTAAATTAAATCTTCACATC" +
                                "AATTCCCTGGAGCTTGGTGACTCTGCTGTGTATTTCTGTGCCAGCAGCCAAGA")),
                new FastaRecord<>(-1, "TRBJ00",
                        new NucleotideSequence("AGAATAGTGGAGGTAGCAACTATAAACTGACATTTGGAAAAGGAACTCTCTTAACCGTGAATCCAA")),
                new FastaRecord<>(-1, "TRBD00",
                        new NucleotideSequence("GGGACTAGCGGGGGGG")));

        var lib = FastaSegmentLibraryUtils.createUsingTemplate(records,
                Species.Human, Gene.TRB);

        System.out.println(lib);

        Assert.assertEquals(1, lib.getAllV().size());
        Assert.assertEquals(1, lib.getAllD().size());
        Assert.assertEquals(1, lib.getAllJ().size());
        Assert.assertEquals(0, lib.getAllC().size());

        var vMarkup=lib.getV("TRBV00").getRegionMarkupAa();
        for (AntigenReceptorRegionType region : AntigenReceptorRegionType.values()) {
            Assert.assertEquals((region == AntigenReceptorRegionType.FR4), vMarkup.getRegion(region).isEmpty());
        }

        Assert.assertTrue(lib.getV("TRBV00").getCdr3Part().toString().startsWith("TGT"));
        Assert.assertTrue(lib.getJ("TRBJ00").getCdr3Part().toString().endsWith("TTT"));
    }

    @Test
    public void muruganTest() {
        var lib = FastaSegmentLibraryUtils.loadMurugan(Species.Human, Gene.TRB);

        System.out.println(lib);
    }
}