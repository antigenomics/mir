package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

public final class ArrayBasedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        extends SequenceRegionMarkup<S, E> {
    private final int[] markup;

    public ArrayBasedSequenceRegionMarkup(S fullSequence, int[] markup, Class<E> regionTypeClass) {
        super(fullSequence, regionTypeClass);
        this.markup = markup;
        if (markup.length != regionTypeClass.getEnumConstants().length + 1) {
            throw new IllegalArgumentException("Bad markup - wrong number of points: " + Arrays.toString(markup));
        }
        if (markup[0] < 0) {
            throw new IllegalArgumentException("Negative values in markup");
        }
        for (int i = 1; i < markup.length; i++) {
            if (markup[i - 1] > markup[i]) {
                throw new IllegalArgumentException("Bad markup - wrong order/negative values: " +
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

    public PrecomputedSequenceRegionMarkup<S, E> asPrecomputed() {
        return new PrecomputedSequenceRegionMarkup<>(fullSequence, getAllRegions(), regionTypeClass);
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> merge(SequenceRegionMarkup<S, E> other) {
        if (other instanceof ArrayBasedSequenceRegionMarkup) {
            var otherConv = (ArrayBasedSequenceRegionMarkup<S, E>) other;

            if (!this.fullSequence.equals(other.fullSequence)) {
                throw new IllegalArgumentException("Markups were computed for different sequences");
            }

            int[] newMarkup = markup.clone();

            for (int i = 0; i < newMarkup.length; i++) {
                int x = newMarkup[i], y = otherConv.markup[i];
                newMarkup[i] = Math.max(x, y);
            }

            return new ArrayBasedSequenceRegionMarkup<>(fullSequence, newMarkup, regionTypeClass);
        } else if (other instanceof PrecomputedSequenceRegionMarkup) {
            return merge(((PrecomputedSequenceRegionMarkup<S, E>) other).asArrayBased());
        } else {
            throw new IllegalArgumentException("Don't know how to merge with " + other.getClass());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayBasedSequenceRegionMarkup<?, ?> that = (ArrayBasedSequenceRegionMarkup<?, ?>) o;
        //System.out.println(Arrays.toString(markup));
        //System.out.println(Arrays.toString(that.markup));
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
