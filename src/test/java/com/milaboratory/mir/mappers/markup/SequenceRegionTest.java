package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceRegionTest {
    @Test
    public void createTest() {
        new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence("ATGC"), 0, 4, true);
        new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence("ATGC"), 5, 9, true);
    }

    @Test
    public void emptyTest() {
        var emptyRegion = new SequenceRegion<>(DummyRegion.R1,
                new NucleotideSequence(""), -1, -1, true);
        assertEquals(SequenceRegion.empty(DummyRegion.R1, NucleotideSequence.ALPHABET),
                emptyRegion);
    }

    @Test
    public void sortTest() {
        assertTrue(
                SequenceRegion.empty(DummyRegion.R1, NucleotideSequence.ALPHABET)
                        .compareTo(
                                SequenceRegion.empty(DummyRegion.R2, NucleotideSequence.ALPHABET)
                        ) < 0);

        assertTrue(
                new SequenceRegion<>(DummyRegion.R1,
                        new NucleotideSequence("ATGC"), 0, 4, true)
                        .compareTo(
                                new SequenceRegion<>(DummyRegion.R1,
                                        new NucleotideSequence("TCGA"), 5, 9, true)
                        ) < 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTest1() {
        new SequenceRegion<>(DummyRegion.R1, new NucleotideSequence("A"), -1, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTest2() {
        new SequenceRegion<>(DummyRegion.R1, new NucleotideSequence("ATGC"), 0, 3, true);
    }
}