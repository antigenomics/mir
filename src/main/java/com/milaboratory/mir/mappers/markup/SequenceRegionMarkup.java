package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;
import java.util.stream.Collectors;

public abstract class SequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>> {
    protected final S fullSequence;
    protected final Class<E> regionTypeClass;

    public SequenceRegionMarkup(S fullSequence, Class<E> regionTypeClass) {
        this.fullSequence = fullSequence;
        this.regionTypeClass = regionTypeClass;
    }

    public S getFullSequence() {
        return fullSequence;
    }

    public abstract SequenceRegion<S, E> getRegion(E regionType);

    public Class<E> getRegionTypeClass() {
        return regionTypeClass;
    }

    public abstract EnumMap<E, SequenceRegion<S, E>> getAllRegions();

    public abstract SequenceRegionMarkup<S, E> merge(SequenceRegionMarkup<S, E> other);

    public static <S extends Sequence<S>, E extends Enum<E>> SequenceRegionMarkup<S, E> merge(
            SequenceRegionMarkup<S, E> first,
            SequenceRegionMarkup<S, E> second) {
        return first.merge(second);
    }

    @Override
    public String toString() {
        return getAllRegions().values().stream()
                .map(SequenceRegion::toString)
                .collect(Collectors.joining("\n"));
    }
}
