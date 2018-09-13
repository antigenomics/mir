package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;
import java.util.stream.Collectors;

public abstract class SequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>,
        M extends SequenceRegionMarkup<S, E, M>> {
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

    public abstract <M2 extends SequenceRegionMarkup<S, E, M2>> M concatenate(M2 other);

    public abstract M realign(S querySequence, Alignment<S> alignment);

    @Override
    public String toString() {
        return getAllRegions().values().stream()
                .map(SequenceRegion::toString)
                .collect(Collectors.joining("\n"));
    }
}
