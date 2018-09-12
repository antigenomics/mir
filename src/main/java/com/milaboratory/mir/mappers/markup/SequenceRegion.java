package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.*;

import java.util.Objects;

public class SequenceRegion<S extends Sequence<S>, E extends Enum<E>>
        implements Comparable<SequenceRegion<S, E>> {
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
        if (start <= -1 || end <= -1) {
            if (start + end != -2) {
                throw new IllegalArgumentException("Negative start/end only allowed when start=end=-1.");
            }
        }
        if (sequence.size() != end - start) {
            throw new IllegalArgumentException("Region boundaries do not match sequence length");
        }
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

    public int getSize() {
        return sequence.size();
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

    @Override
    public String toString() {
        return regionType + "\t" + start + "\t" + end + "\t" + sequence + "\t" + incomplete;
    }

    @Override
    public int compareTo(SequenceRegion<S, E> o) {
        int res = Integer.compare(regionType.ordinal(), o.regionType.ordinal());
        res = res == 0 ? Integer.compare(start, o.start) : res;
        return res == 0 ? Integer.compare(end, o.end) : res;
    }
}
