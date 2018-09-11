package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

import java.util.Collection;
import java.util.EnumMap;

public class PrecomputedSequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>>
        implements SequenceRegionMarkup<S, E> {
    private final EnumMap<E, SequenceRegion<S, E>> regionMap;
    private final S fullSequence;
    private final Class<E> regionTypeClass;

    public PrecomputedSequenceRegionMarkup(EnumMap<E, SequenceRegion<S, E>> regionMap,
                                           S fullSequence,
                                           Class<E> regionTypeClass) {
        this.regionMap = regionMap;
        this.fullSequence = fullSequence;
        this.regionTypeClass = regionTypeClass;
    }

    public PrecomputedSequenceRegionMarkup(Collection<SequenceRegion<S, E>> regions,
                                           S fullSequence,
                                           Class<E> regionTypeClass) {
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
        this.fullSequence = fullSequence;
        this.regionTypeClass = regionTypeClass;
    }

    @Override
    public S getFullSequence() {
        return fullSequence;
    }

    @Override
    public SequenceRegion<S, E> getRegion(E regionType) {
        return regionMap.get(regionType);
    }

    @Override
    public Class<E> getRegionTypeClass() {
        return regionTypeClass;
    }

    @Override
    public EnumMap<E, SequenceRegion<S, E>> getAllRegions() {
        return regionMap;
    }
}
