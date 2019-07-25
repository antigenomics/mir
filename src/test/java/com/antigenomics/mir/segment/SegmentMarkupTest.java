package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Sequence;
import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SegmentMarkupTest {
    @Test
    public void checkRegionsV() throws IOException {
        var v = MigecSegmentLibraryUtils
                .getLibraryFromResources(Species.Human, Gene.TRA)
                .getV("TRAV23/DV6*01");
        matchSequence(
                "CAGCAGCAGGTGAAACAAAGTCCTCAATCTTTGATAGTCCAGAAAGGAGGGATTTCAATTATAAACTGTGCTTATGAG",
                v.getRegion(SegmentRegionType.FR1NT)
        );
        matchSequence(
                "AACACTGCGTTTGACTAC",
                v.getRegion(SegmentRegionType.CDR1NT)
        );
        matchSequence(
                "TTTCCATGGTACCAACAATTCCCTGGGAAAGGCCCTGCATTATTGATAGCC",
                v.getRegion(SegmentRegionType.FR2NT)
        );
        matchSequence(
                "ATACGTCCAGATGTGAGTGAA",
                v.getRegion(SegmentRegionType.CDR2NT)
        );
        matchSequence(
                "AAGAAAGAAGGAAGATTCACAATCTCCTTCAATAAAAGTGCCAAGCAGTTCTCATTGCATATCATGGATTCCCAGCCTGGAGACTCAGCCACCTACTTC",
                v.getRegion(SegmentRegionType.FR3NT)
        );
        matchSequence(
                "TGTGCAGCAAGCA",
                v.getRegion(SegmentRegionType.CDR3NT)
        );

        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR4, NucleotideSequence.ALPHABET,
                        v.getGermlineSequenceNt().size()),
                v.getRegion(SegmentRegionType.FR4NT)
        );

        matchSequence(
                " Q  Q  Q  V  K  Q  S  P  Q  S  L  I  V  Q  K  G  G  I  S  I  I  N  C  A  Y  E ",
                v.getRegion(SegmentRegionType.FR1AA)
        );
        matchSequence(
                " N  T  A  F  D  Y ",
                v.getRegion(SegmentRegionType.CDR1AA)
        );
        matchSequence(
                " F  P  W  Y  Q  Q  F  P  G  K  G  P  A  L  L  I  A ",
                v.getRegion(SegmentRegionType.FR2AA)
        );
        matchSequence(
                " I  R  P  D  V  S  E ",
                v.getRegion(SegmentRegionType.CDR2AA)
        );
        matchSequence(
                " K  K  E  G  R  F  T  I  S  F  N  K  S  A  K  Q  F  S  L  H  I  M  D  S  Q  P  G  D  S  A  T  Y  F ",
                v.getRegion(SegmentRegionType.FR3AA)
        );
        matchSequence(
                " C  A  A  S ",
                v.getRegion(SegmentRegionType.CDR3AA)
        );

        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR4,
                        AminoAcidSequence.ALPHABET,
                        v.getGermlineSequenceNt().size() / 3),
                v.getRegion(SegmentRegionType.FR4AA)
        );
    }

    @Test
    public void checkRegionsJ() throws IOException {
        var j = MigecSegmentLibraryUtils
                .getLibraryFromResources(Species.Human, Gene.TRA)
                .getJ("TRAJ50*01");

        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR1, NucleotideSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR1NT)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.CDR1, NucleotideSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.CDR1NT)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR2, NucleotideSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR2NT)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.CDR2, NucleotideSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.CDR2NT)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR3, NucleotideSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR3NT)
        );
        matchSequence(
                "TGAAAACCTCCTACGACAAGGTGATATTT",
                j.getRegion(SegmentRegionType.CDR3NT)
        );
        matchSequence(
                "GGGCCAGGGACAAGCTTATCAGTCATTCCAA",
                j.getRegion(SegmentRegionType.FR4NT)
        );

        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR1, AminoAcidSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR1AA)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.CDR1, AminoAcidSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.CDR1AA)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR2, AminoAcidSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR2AA)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.CDR2, AminoAcidSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.CDR2AA)
        );
        Assert.assertEquals(
                SequenceRegion.empty(AntigenReceptorRegionType.FR3, AminoAcidSequence.ALPHABET, 0),
                j.getRegion(SegmentRegionType.FR3AA)
        );
        matchSequence(
                " K  T  S  Y  D  K  V  I  F ",
                j.getRegion(SegmentRegionType.CDR3AA)
        );
        matchSequence(
                " G  P  G  T  S  L  S  V  I  P ",
                j.getRegion(SegmentRegionType.FR4AA)
        );
    }


    private static void matchSequence(String expected, SequenceRegion sequenceRegion) {
        matchSequence(expected, sequenceRegion.getSequence());
    }

    @SuppressWarnings("unchecked")
    private static void matchSequence(String expected, Sequence observed) {
        expected = expected.replaceAll(" +", ""); // igblast copy-paste
        var expectedSeq = (observed instanceof AminoAcidSequence ?
                new AminoAcidSequence(expected) : new NucleotideSequence(expected));
        Assert.assertEquals(expectedSeq, observed);
    }
}
