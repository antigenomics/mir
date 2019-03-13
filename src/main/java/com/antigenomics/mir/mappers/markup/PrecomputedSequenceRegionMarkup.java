package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;
import java.util.Objects;

public final class PrecomputedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        extends SequenceRegionMarkup<S, E, PrecomputedSequenceRegionMarkup<S, E>> {
    private final EnumMap<E, SequenceRegion<S, E>> regionMap;
    private final int start, end;

    public static <S extends Sequence<S>, E extends Enum<E>> PrecomputedSequenceRegionMarkup<S, E> empty(
            Alphabet<S> alphabet,
            Class<E> regionTypeClass) {
        S fullSequence = alphabet.getEmptySequence();
        var regionMap = new EnumMap<E, SequenceRegion<S, E>>(regionTypeClass);
        for (E regionType : regionTypeClass.getEnumConstants()) {
            regionMap.put(regionType, SequenceRegion.empty(regionType, alphabet, 0));
        }
        return new PrecomputedSequenceRegionMarkup<>(fullSequence,
                regionMap, regionTypeClass, true);
    }

    public PrecomputedSequenceRegionMarkup(S fullSequence,
                                           EnumMap<E, SequenceRegion<S, E>> regionMap,
                                           Class<E> regionTypeClass) {
        this(fullSequence, regionMap, regionTypeClass, false);
    }

    PrecomputedSequenceRegionMarkup(S fullSequence,
                                    EnumMap<E, SequenceRegion<S, E>> regionMap,
                                    Class<E> regionTypeClass,
                                    boolean unsafe) {
        super(fullSequence, regionTypeClass);
        if (regionMap.size() != regionTypeClass.getEnumConstants().length) {
            throw new IllegalArgumentException("Bad markup - wrong number of regions: " + regionMap);
        }
        // todo: wrap all in unsafe to speed up in non-debug mode
        this.regionMap = unsafe ? regionMap : new EnumMap<>(regionMap);
        SequenceRegion<S, E> previous = null;
        for (SequenceRegion<S, E> region : regionMap.values()) {
            if (previous == null || previous.getEnd() == region.getStart()) {
                previous = region;
            } else {
                throw new IllegalArgumentException("Regions not tightly before each other:\n" +
                        previous + "\n" + region);
            }
        }
        E[] regionTypes = regionTypeClass.getEnumConstants();
        this.start = regionMap.get(regionTypes[0]).getStart();
        this.end = regionMap.get(regionTypes[regionTypes.length - 1]).getStart();
    }

    @Override
    public SequenceRegion<S, E> getRegion(E regionType) {
        return regionMap.get(regionType);
    }

    @Override
    public EnumMap<E, SequenceRegion<S, E>> getAllRegions() {
        return regionMap;
    }

    @Override
    public <M2 extends SequenceRegionMarkup<S, E, M2>> PrecomputedSequenceRegionMarkup<S, E> concatenate(M2 other) {
        // todo: better impl?
        return asArrayBased().concatenate(other).asPrecomputed();
    }

    @Override
    public PrecomputedSequenceRegionMarkup<S, E> realign(S querySequence, Alignment<S> alignment) {
        if (!Objects.equals(alignment.getSequence1(), fullSequence)) {
            throw new IllegalArgumentException("Cannot realign - alignment was performed for another sequence");
        }

        var newRegionMap = new EnumMap<E, SequenceRegion<S, E>>(regionTypeClass);

        for (SequenceRegion<S, E> region : regionMap.values()) {
            int start = SequenceRegionMarkupUtils.targetToQueryPosition(region.getStart(), alignment),
                    end = SequenceRegionMarkupUtils.targetToQueryPosition(region.getEnd(), alignment);

            newRegionMap.put(region.getRegionType(),
                    SequenceRegion.cutFrom(region.getRegionType(), querySequence, start, end));
        }

        return new PrecomputedSequenceRegionMarkup<>(querySequence, newRegionMap, regionTypeClass, true);
    }

    @Override
    public PrecomputedSequenceRegionMarkup<S, E> padLeft(S sequence) {
        if (sequence.size() == 0) {
            return this;
        }

        var newRegionMap = new EnumMap<E, SequenceRegion<S, E>>(regionTypeClass);

        for (SequenceRegion<S, E> region : regionMap.values()) {
            newRegionMap.put(region.getRegionType(),
                    region.shift(sequence.size()));
        }

        return new PrecomputedSequenceRegionMarkup<>(sequence.concatenate(fullSequence),
                newRegionMap, regionTypeClass, true);
    }

    @Override
    public PrecomputedSequenceRegionMarkup<S, E> padRight(S sequence) {
        if (sequence.size() == 0) {
            return this;
        }

        return new PrecomputedSequenceRegionMarkup<>(fullSequence.concatenate(sequence),
                regionMap, regionTypeClass, true);
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public ArrayBasedSequenceRegionMarkup<S, E> asArrayBased() {
        int n = regionTypeClass.getEnumConstants().length;
        int[] markup = new int[n + 1];
        for (SequenceRegion<S, E> sequenceRegion : regionMap.values()) {
            int i = sequenceRegion.getRegionType().ordinal();
            markup[i] = sequenceRegion.getStart();
            if (i == n - 1) {
                markup[i + 1] = sequenceRegion.getEnd();
            }
        }
        return new ArrayBasedSequenceRegionMarkup<>(fullSequence, markup, regionTypeClass, true);
    }

    @Override
    public PrecomputedSequenceRegionMarkup<S, E> asPrecomputed() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrecomputedSequenceRegionMarkup<?, ?> that = (PrecomputedSequenceRegionMarkup<?, ?>) o;
        return Objects.equals(regionMap, that.regionMap) &&
                Objects.equals(fullSequence, that.fullSequence) &&
                Objects.equals(regionTypeClass, that.regionTypeClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionMap, fullSequence, regionTypeClass);
    }
}