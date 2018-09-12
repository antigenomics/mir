package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Objects;

public class PrecomputedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        extends SequenceRegionMarkup<S, E> {
    private final EnumMap<E, SequenceRegion<S, E>> regionMap;

    public static <S extends Sequence<S>, E extends Enum<E>> PrecomputedSequenceRegionMarkup<S, E> empty(
            Alphabet<S> alphabet,
            Class<E> regionTypeClass) {
        S fullSequence = alphabet.getEmptySequence();
        var regionMap = new EnumMap<E, SequenceRegion<S, E>>(regionTypeClass);
        for (E regionType : regionTypeClass.getEnumConstants()) {
            regionMap.put(regionType, SequenceRegion.empty(regionType, alphabet));
        }
        return new PrecomputedSequenceRegionMarkup<>(fullSequence,
                regionMap, regionTypeClass);
    }

    public PrecomputedSequenceRegionMarkup(S fullSequence,
                                           EnumMap<E, SequenceRegion<S, E>> regionMap,
                                           Class<E> regionTypeClass) {
        super(fullSequence, regionTypeClass);
        this.regionMap = regionMap;
    }

    public PrecomputedSequenceRegionMarkup(S fullSequence,
                                           Collection<SequenceRegion<S, E>> regions,
                                           Class<E> regionTypeClass) {
        super(fullSequence, regionTypeClass);
        this.regionMap = new EnumMap<>(regionTypeClass);
        if (regionMap.size() != regionTypeClass.getEnumConstants().length) {
            throw new IllegalArgumentException("Region map doesn't contain all regions");
        }
        for (SequenceRegion<S, E> region : regions) {
            if (regionMap.containsKey(region.getRegionType())) {
                throw new IllegalArgumentException("Region map doesn't contain all regions");
            }
            regionMap.put(region.getRegionType(), region);
        }
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
    public ArrayBasedSequenceRegionMarkup<S, E> merge(SequenceRegionMarkup<S, E> other) {
        // todo: maybe better impl
        return asArrayBased().merge(other);
    }

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
        return new ArrayBasedSequenceRegionMarkup<>(fullSequence, markup, regionTypeClass);
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
