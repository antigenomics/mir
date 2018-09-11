package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;

import java.util.Objects;

public class SequenceRegion<S extends Sequence<S>, E extends Enum<E>> {
    public static <S extends Sequence<S>, E extends Enum<E>> SequenceRegion<S, E> empty(E regionType,
                                                                                        Alphabet<S> alphabet) {
        return new SequenceRegion<>(regionType, alphabet.getEmptySequence(), -1, -1, true);
    }

    private final E regionType;
    private final S sequence;
    private final int start, end;
    private final boolean incomplete;

    public SequenceRegion(E regionType, S sequence,
                          int start, int end,
                          boolean incomplete) {
        this.start = start;
        this.end = end;
        this.regionType = regionType;
        this.sequence = sequence;
        this.incomplete = incomplete;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public E getRegionType() {
        return regionType;
    }

    public S getSequence() {
        return sequence;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    public boolean isEmpty() {
        return sequence.size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequenceRegion<?, ?> that = (SequenceRegion<?, ?>) o;
        return start == that.start &&
                end == that.end &&
                Objects.equals(regionType, that.regionType) &&
                Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, regionType, sequence);
    }
}
