package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class MarkupGenerator {
    private static final Random rnd = new Random(51102);
    private static final NucleotideSequence seq;

    static {
        final char[] seqChars = new char[Byte.MAX_VALUE - Byte.MIN_VALUE];
        Arrays.fill(seqChars, 'G'); // translates to glycine :)
        seq = new NucleotideSequence(seqChars);
    }

    public static ArrayBasedSequenceRegionMarkup<NucleotideSequence, DummyRegion> generate() {
        int n = DummyRegion.class.getEnumConstants().length + 1;
        byte[] points = new byte[n];
        rnd.nextBytes(points);
        Arrays.sort(points);
        int[] markup = new int[n];
        for (int i = 0; i < n; i++) {
            markup[i] = points[i] - Byte.MIN_VALUE;
        }
        return new ArrayBasedSequenceRegionMarkup<>(seq, markup, DummyRegion.class);
    }

    public static void randomTest(Consumer<ArrayBasedSequenceRegionMarkup<NucleotideSequence, DummyRegion>> test,
                                  int n) {
        for (int i = 0; i < n; i++) {
            test.accept(generate());
        }
    }
}
