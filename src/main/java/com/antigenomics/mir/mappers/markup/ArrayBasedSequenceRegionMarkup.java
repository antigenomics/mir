package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

public final class ArrayBasedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        extends SequenceRegionMarkup<S, E, ArrayBasedSequenceRegionMarkup<S, E>> {
    private final int[] markup;

    public static <S extends Sequence<S>, E extends Enum<E>> ArrayBasedSequenceRegionMarkup<S, E> empty(
            Alphabet<S> alphabet,
            Class<E> regionTypeClass) {
        return new ArrayBasedSequenceRegionMarkup<>(alphabet.getEmptySequence(),
                new int[regionTypeClass.getEnumConstants().length + 1], regionTypeClass, true);
    }

    public ArrayBasedSequenceRegionMarkup(S fullSequence, int[] markup, Class<E> regionTypeClass) {
        this(fullSequence, markup, regionTypeClass, false);
    }

    ArrayBasedSequenceRegionMarkup(S fullSequence, int[] markup, Class<E> regionTypeClass, boolean unsafe) {
        super(fullSequence, regionTypeClass);

        this.markup = unsafe ? markup : markup.clone();
        if (markup.length != regionTypeClass.getEnumConstants().length + 1) {
            throw new IllegalArgumentException("Bad markup - wrong number of points: " + Arrays.toString(markup));
        }
        if (markup[0] < 0) {
            throw new IllegalArgumentException("Negative values in markup");
        }
        for (int i = 1; i < markup.length; i++) {
            if (markup[i - 1] > markup[i] || markup[i] > fullSequence.size()) {
                throw new IllegalArgumentException("Bad markup - wrong order/negative values/out of bounds: " +
                        Arrays.toString(markup));
            }
        }
    }

    @Override
    public SequenceRegion<S, E> getRegion(E regionType) {
        int startIndex = regionType.ordinal();
        int start = markup[startIndex], end = markup[startIndex + 1];
        return start == end ?
                SequenceRegion.empty(regionType, fullSequence.getAlphabet(), start) :
                new SequenceRegion<>(regionType, fullSequence.getRange(start, end), start, end);
    }

    @Override
    public EnumMap<E, SequenceRegion<S, E>> getAllRegions() {
        var map = new EnumMap<E, SequenceRegion<S, E>>(regionTypeClass);
        for (E regionType : regionTypeClass.getEnumConstants()) {
            map.put(regionType, getRegion(regionType));
        }
        return map;
    }

    @Override
    public <M2 extends SequenceRegionMarkup<S, E, M2>> ArrayBasedSequenceRegionMarkup<S, E> concatenate(M2 other) {
        if (!Objects.equals(fullSequence, other.fullSequence)) {
            throw new IllegalArgumentException("Cannot concatenate markups computed for different sequences.");
        }

        if (other.getStart() < getStart() || other.getEnd() < getEnd()) {
            throw new IllegalArgumentException("Cannot concatenate: " +
                    "embedded markups / other markup comes before this one.");
        }

        if (other instanceof ArrayBasedSequenceRegionMarkup) {
            return new ArrayBasedSequenceRegionMarkup<>(fullSequence,
                    concatenateArr(markup, ((ArrayBasedSequenceRegionMarkup) other).markup),
                    regionTypeClass, true);
        } else {
            int[] newMarkup = markup.clone();
            int i = 0;
            for (SequenceRegion<S, E> region : other.getAllRegions().values()) {
                update(i++, newMarkup, region.getStart(), region.getEnd());
            }
            return new ArrayBasedSequenceRegionMarkup<>(fullSequence, newMarkup, regionTypeClass, true);
        }
    }

    static void update(int i, int[] arr1, int start2, int end2) {
        int start1 = arr1[i], end1 = arr1[i + 1];
        if (start2 < end2) {
            if (start1 < end1) {
                // extend
                arr1[i + 1] = end2;
            } else {
                // copy from second
                arr1[i] = start2;
                arr1[i + 1] = end2;
            }
        } else if (start1 >= arr1[arr1.length - 1]) {
            // copy empty regions from second
            arr1[i] = start2;
            arr1[i + 1] = end2;
        }
    }

    static int[] concatenateArr(int[] arr1, int[] arr2) {
        arr1 = arr1.clone();

        for (int i = 0; i < arr1.length - 1; i++) {
            update(i, arr1, arr2[i], arr2[i + 1]);
        }

        return arr1;
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> realign(S querySequence, Alignment<S> alignment) {
        if (!Objects.equals(alignment.getSequence1(), fullSequence)) {
            System.out.println("Cannot realign - alignment was performed for another sequence");
        }

        int[] newMarkup = new int[markup.length];
        int lastPos = markup.length - 1;
        for (int i = 0; i < lastPos; i++) {
            newMarkup[i] = SequenceRegionMarkupUtils.targetToQueryPosition(markup[i], alignment);
        }

        newMarkup[lastPos] = SequenceRegionMarkupUtils.targetToQueryPosition(markup[lastPos], alignment);

        return new ArrayBasedSequenceRegionMarkup<>(fullSequence, newMarkup, regionTypeClass, true);
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> padLeft(S sequence) {
        if (sequence.size() == 0) {
            return this;
        }

        int[] newMarkup = markup.clone();
        for (int i = 0; i < markup.length; i++) {
            newMarkup[i] += sequence.size();
        }
        return new ArrayBasedSequenceRegionMarkup<>(sequence.concatenate(fullSequence),
                newMarkup, regionTypeClass, true);
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> padRight(S sequence) {
        if (sequence.size() == 0) {
            return this;
        }

        return new ArrayBasedSequenceRegionMarkup<>(fullSequence.concatenate(sequence),
                markup, regionTypeClass, true);
    }

    @Override
    public int getStart() {
        return markup[0];
    }

    @Override
    public int getEnd() {
        return markup[markup.length - 1];
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> asArrayBased() {
        return this;
    }

    @Override
    public PrecomputedSequenceRegionMarkup<S, E> asPrecomputed() {
        return new PrecomputedSequenceRegionMarkup<>(fullSequence, getAllRegions(), regionTypeClass, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayBasedSequenceRegionMarkup<?, ?> that = (ArrayBasedSequenceRegionMarkup<?, ?>) o;

        return Arrays.equals(markup, that.markup) &&
                Objects.equals(fullSequence, that.fullSequence) &&
                Objects.equals(regionTypeClass, that.regionTypeClass);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fullSequence, regionTypeClass);
        result = 31 * result + Arrays.hashCode(markup);
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(markup) + "\n" + super.toString();
    }
}