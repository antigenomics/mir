package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.Range;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;
import java.util.stream.Collectors;

public abstract class SequenceRegionMarkup<S extends Sequence<S>, E extends Enum<E>,
        M extends SequenceRegionMarkup<S, E, M>> {
    protected final S fullSequence;
    protected final Class<E> regionTypeClass;
    protected final double score;

    public SequenceRegionMarkup(S fullSequence, Class<E> regionTypeClass, double score) {
        this.fullSequence = fullSequence;
        this.regionTypeClass = regionTypeClass;
        this.score = score;
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

    public abstract M padLeft(S sequence);

    public abstract M padRight(S sequence);

    public abstract int getStart();

    public abstract int getEnd();

    public double getScore() {
        return score;
    }

    public static <S extends Sequence<S>> double computeScore(S querySequence, Alignment<S> alignment) {
        return (alignment.getSequence2Range().length() - alignment.getAbsoluteMutations().size()) /
                (double) querySequence.size();
    }

    public abstract ArrayBasedSequenceRegionMarkup<S, E> asArrayBased();

    public abstract PrecomputedSequenceRegionMarkup<S, E> asPrecomputed();

    public Range getSpan() {
        return new Range(getStart(), getEnd());
    }

    @Override
    public String toString() {
        return getAllRegions().values().stream()
                .map(SequenceRegion::toString)
                .collect(Collectors.joining("\n"));
    }
}
