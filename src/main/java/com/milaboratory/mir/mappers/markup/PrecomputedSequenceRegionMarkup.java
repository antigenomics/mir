package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.Collection;
import java.util.EnumMap;

public class PrecomputedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        extends SequenceRegionMarkup<S, E> {
    private final EnumMap<E, SequenceRegion<S, E>> regionMap;

    public PrecomputedSequenceRegionMarkup(EnumMap<E, SequenceRegion<S, E>> regionMap,
                                           S fullSequence,
                                           Class<E> regionTypeClass) {
        super(fullSequence, regionTypeClass);
        this.regionMap = regionMap;
    }

    public PrecomputedSequenceRegionMarkup(Collection<SequenceRegion<S, E>> regions,
                                           S fullSequence,
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
        return new ArrayBasedSequenceRegionMarkup<>(fullSequence, regionTypeClass, markup);
    }
}
