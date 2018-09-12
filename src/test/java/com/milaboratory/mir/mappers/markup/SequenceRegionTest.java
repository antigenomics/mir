package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceRegionTest {
    @Test
    public void createTest() {
        new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence("ATGC"), 0, 4);
        new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence("ATGC"), 5, 9);
    }

    @Test
    public void emptyTest() {
        var emptyRegion = new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence(""), 11, 11);
        assertEquals(SequenceRegion.empty(DummyRegion.R1, NucleotideSequence.ALPHABET, 11),
                emptyRegion);
    }

    @Test
    public void sortTest() {
        assertTrue(
                SequenceRegion.empty(DummyRegion.R1, NucleotideSequence.ALPHABET, 1)
                        .compareTo(
                                SequenceRegion.empty(DummyRegion.R2, NucleotideSequence.ALPHABET, 1)
                        ) < 0);

        assertTrue(
                new SequenceRegion<>(DummyRegion.R1,
                        new NucleotideSequence("ATGC"), 0, 4)
                        .compareTo(
                                new SequenceRegion<>(DummyRegion.R1,
                                        new NucleotideSequence("TCGA"), 5, 9)
                        ) < 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTest1() {
        new SequenceRegion<>(DummyRegion.R1, new NucleotideSequence("A"), -1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTest2() {
        new SequenceRegion<>(DummyRegion.R1, new NucleotideSequence("ATGC"), 0, 3);
    }
}