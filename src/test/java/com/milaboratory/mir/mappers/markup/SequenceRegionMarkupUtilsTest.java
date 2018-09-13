package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Assert;
import org.junit.Test;

import java.util.EnumMap;

public class SequenceRegionMarkupUtilsTest {
//    @Test
//    public void translateRegionTest() {
//        var parent = new NucleotideSequence("GAAGCTGGAGTTGCCCAGTCTCCCAGATATAAGATTATAGAGAAAAGGCAGAGTGTGGCTTTTTGGTGCA" +
//                "ATCCTATATCTGGCCATGCTACCCTTTACTGGTACCAGCAGATCCTGGGACAGGGCCCAAAGCTTCTGATTCAGTTTCAGAATAACGGTGTAGTGGAT" +
//                "GATTCACAGTTGCCTAAGGATCGATTTTCTGCAGAGAGGCTCAAAGGAGTAGACTCCACTCTCAAGATCCAGCCTGCAAAGCTTGAGGACTCGGCCGT" +
//                "GTATCTCTGTGCCAGCAGCTTAGA");
//
//        Assert.assertEquals(
//                new SequenceRegion<>(DummyRegion.R1,
//                        new AminoAcidSequence("SGHAT"), 26, 31),
//                SequenceRegionMarkupUtils.translate(
//                        new SequenceRegion<>(DummyRegion.R1,
//                                new NucleotideSequence("TCTGGCCATGCTACC"), 78, 93),
//                        false,
//                        parent
//                ));
//
//        Assert.assertEquals(
//                new SequenceRegion<>(DummyRegion.R2,
//                        new AminoAcidSequence("CASSL"), 91, 96),
//                SequenceRegionMarkupUtils.translate(
//                        new SequenceRegion<>(DummyRegion.R2,
//                                new NucleotideSequence("TGTGCCAGCAGCTTAGA"), 276 - 3, 290),
//                        false,
//                        parent
//                ));
//
//        var parent2 = parent.concatenate(new NucleotideSequence("CTCCTACAATGAGCAGTTCTTCGGGCCAGGGACACGGCTCACCGTGCTAG"));
//
//        Assert.assertEquals(
//                new SequenceRegion<>(DummyRegion.R3,
//                        new AminoAcidSequence("SYNEQFF"), 97, 97 + 7),
//                SequenceRegionMarkupUtils.translate(
//                        new SequenceRegion<>(DummyRegion.R3,
//                                new NucleotideSequence("CTCCTACAATGAGCAGTTCTTC"), 290, 290 + 22),
//                        true,
//                        parent2
//                ));
//
//        Assert.assertEquals(
//                new SequenceRegion<>(DummyRegion.R4,
//                        new AminoAcidSequence("GPGTRLTVL"), 97 + 7, 97 + 7 + 9),
//                SequenceRegionMarkupUtils.translate(
//                        new SequenceRegion<>(DummyRegion.R4,
//                                new NucleotideSequence("GGGCCAGGGACACGGCTCACCGTGCTAG"), 290 + 22, 290 + 22 + 28),
//                        false,
//                        parent2
//                ));
//    }

    private NucleotideSequence parent = new NucleotideSequence(
            "GAAGCTGGAGTTGCCCAGTCTCCCAGATATAAGATTATAGAGAAAAGGCAGAGTGTGGCTTTTTGGTGCAATCCTATATCTGGCCATGCTACCCTTTACTGGT" +
                    "ACCAGCAGATCCTGGGACAGGGCCCAAAGCTTCTGATTCAGTTTCAGAATAACGGTGTAGTGGATGATTCACAGTTGCCTAAGGATCGATTTTCT" +
                    "GCAGAGAGGCTCAAAGGAGTAGACTCCACTCTCAAGATCCAGCCTGCAAAGCTTGAGGACTCGGCCGTGTATCTCTGTGCCAGCAGCTTAGA" +
                    "CTCCTACAATGAGCAGTTCTTCGGGCCAGGGACACGGCTCACCGTGCTAG");

    @Test
    public void translateMarkupTest1() {
        var regionMap = new EnumMap<DummyRegion, SequenceRegion<NucleotideSequence, DummyRegion>>(DummyRegion.class);
        regionMap.put(DummyRegion.R1, new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence("GTGGATGATTCACAGTTGCCTAAGGATCGATTTTCTGCAGAGAGGCTCAAAGGAGTAGACTCCACTCTCAAGATC" +
                        "CAGCCTGCAAAGCTTGAGGACTCGGCCGTGTATCTC"), 162, 276 - 3));
        regionMap.put(DummyRegion.R2, new SequenceRegion<>(DummyRegion.R2,
                new NucleotideSequence("TGTGCCAGCAGCTTAGA"), 276 - 3, 290));
        regionMap.put(DummyRegion.R3, SequenceRegion.empty(DummyRegion.R3, NucleotideSequence.ALPHABET, 290));
        regionMap.put(DummyRegion.R4, SequenceRegion.empty(DummyRegion.R4, NucleotideSequence.ALPHABET, 290));
        var markup = new PrecomputedSequenceRegionMarkup<>(parent, regionMap, DummyRegion.class);
        var transl = SequenceRegionMarkupUtils.translateWithAnchor(markup, 273);
        Assert.assertEquals(new AminoAcidSequence("CASSL"), transl.getRegion(DummyRegion.R2).getSequence());
        System.out.println(transl);

        var markup2 = markup.asArrayBased();
        var transl2 = SequenceRegionMarkupUtils.translateWithAnchor(markup2, 273);
        Assert.assertEquals(new AminoAcidSequence("CASSL"), transl2.getRegion(DummyRegion.R2).getSequence());
    }

    @Test
    public void translateMarkupTest2() {
        var regionMap = new EnumMap<DummyRegion, SequenceRegion<NucleotideSequence, DummyRegion>>(DummyRegion.class);
        regionMap.put(DummyRegion.R1, SequenceRegion.empty(DummyRegion.R1, NucleotideSequence.ALPHABET, 290));
        regionMap.put(DummyRegion.R2, SequenceRegion.empty(DummyRegion.R2, NucleotideSequence.ALPHABET, 290));
        regionMap.put(DummyRegion.R3, new SequenceRegion<>(DummyRegion.R3,
                new NucleotideSequence("CTCCTACAATGAGCAGTTCTTC"), 290, 290 + 22));
        regionMap.put(DummyRegion.R4, new SequenceRegion<>(DummyRegion.R4,
                new NucleotideSequence("GGGCCAGGGACACGGCTCACCGTGCTAG"), 290 + 22, 290 + 22 + 28));
        var markup = new PrecomputedSequenceRegionMarkup<>(parent, regionMap, DummyRegion.class);
        var transl = SequenceRegionMarkupUtils.translateWithAnchor(markup, 273);
        Assert.assertEquals(new AminoAcidSequence("DSYNEQFF"), transl.getRegion(DummyRegion.R3).getSequence());
        System.out.println(transl);

        var markup2 = markup.asArrayBased();
        var transl2 = SequenceRegionMarkupUtils.translateWithAnchor(markup2, 273);
        Assert.assertEquals(new AminoAcidSequence("DSYNEQFF"), transl2.getRegion(DummyRegion.R3).getSequence());
    }
}
