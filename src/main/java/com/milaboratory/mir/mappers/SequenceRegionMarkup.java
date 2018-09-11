package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;

public interface SequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>> {
    S getFullSequence();

    SequenceRegion<S, E> getRegion(E regionType);

    Class<E> getRegionTypeClass();

    EnumMap<E, SequenceRegion<S, E>> getAllRegions();
}
