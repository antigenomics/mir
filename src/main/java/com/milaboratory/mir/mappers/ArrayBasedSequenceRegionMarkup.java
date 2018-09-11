package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

import java.util.Arrays;
import java.util.EnumMap;

public class ArrayBasedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        implements SequenceRegionMarkup<S, E> {
    private final S fullSequence;
    private final Class<E> regionTypeClass;
    private final int[] markup;

    public ArrayBasedSequenceRegionMarkup(S fullSequence, Class<E> regionTypeClass, int[] markup) {
        this.fullSequence = fullSequence;
        this.regionTypeClass = regionTypeClass;
        this.markup = markup;
        checkMarkup(markup);
    }

    private static void checkMarkup(int[] markup) {
        for (int i = 1; i < markup.length; i++) {
            if (markup[i - 1] >= 0 && markup[i - 1] > markup[i]) {
                throw new IllegalArgumentException("Bad markup: " + Arrays.toString(markup));
            }
        }
    }

    @Override
    public S getFullSequence() {
        return fullSequence;
    }

    private SequenceRegion<S, E> getRegion(E regionType,
                                           int start, int end) {
        if (start >= end || end == 0) {
            return new SequenceRegion<>(regionType,
                    fullSequence.getAlphabet().getEmptySequence(),
                    -1, -1,
                    true);
        }

        boolean incomplete = false;

        if (start < 0) {
            start = 0;
            incomplete = true;
        }

        if (end < 0) {
            end = fullSequence.size();
            incomplete = true;
        }

        return new SequenceRegion<>(regionType,
                fullSequence.getRange(start, end),
                start, end,
                incomplete);
    }

    @Override
    public SequenceRegion<S, E> getRegion(E regionType) {
        int startIndex = regionType.ordinal();

        int start = markup[startIndex];

        if (start < 0) {
            // check next region only - truncation from beginning
            return getRegion(regionType, start, markup[startIndex + 1]);
        } else {
            // find end
            int end = -1;
            for (int i = startIndex + 1; i < 8; i++) {
                end = markup[i];
                if (end >= 0) {
                    break;
                }
            }
            return getRegion(regionType, start, end);
        }
    }

    @Override
    public Class<E> getRegionTypeClass() {
        return regionTypeClass;
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
        return new PrecomputedSequenceRegionMarkup<>(getAllRegions(), fullSequence, regionTypeClass);
    }
}
