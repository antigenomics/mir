package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.Range;
import com.milaboratory.core.sequence.*;

import java.util.Objects;

public final class SequenceRegion<S extends Sequence<S>, E extends Enum<E>>
        implements Comparable<SequenceRegion<S, E>> {
    public static <S extends Sequence<S>, E extends Enum<E>> SequenceRegion<S, E> cutFrom(E regionType,
                                                                                          S sequence,
                                                                                          int start, int end) {
        return new SequenceRegion<>(regionType,
                start < end ? sequence.getRange(start, end) : sequence.getAlphabet().getEmptySequence(),
                start, end);
    }

    public static <S extends Sequence<S>, E extends Enum<E>> SequenceRegion<S, E> empty(E regionType,
                                                                                        Alphabet<S> alphabet,
                                                                                        int position) {
        return new SequenceRegion<>(regionType, alphabet.getEmptySequence(), position, position);
    }

    private final E regionType;
    private final S sequence;
    private final int start, end;

    public SequenceRegion(E regionType, S sequence, Range range) {
        this(regionType, sequence, range.getFrom(), range.getTo());
    }

    public SequenceRegion(E regionType, S sequence,
                          int start, int end) {
        if (start < 0 || start > end) {
            throw new IllegalArgumentException("Bad start/end.");
        }
        if (sequence.size() != end - start) {
            throw new IllegalArgumentException("Region boundaries do not match sequence length: " +
                    sequence.size() + " vs [" + start + "," + end + "]");
        }
        this.start = start;
        this.end = end;
        this.regionType = regionType;
        this.sequence = sequence;
    }

    public boolean contains(int pos) {
        return pos >= start && pos < end;
    }

    public boolean touches(SequenceRegion other) {
        return other.start == end || other.end == start;
    }

    public boolean overlaps(SequenceRegion other) {
        return !(other.start >= end || start >= other.end);
    }

    public boolean before(SequenceRegion other) {
        return end <= other.start;
    }

    public boolean tightBefore(SequenceRegion other) {
        return end == other.start;
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

    public SequenceRegion<S, E> shift(int offset) {
        return new SequenceRegion<>(regionType, sequence, start + offset, end + offset);
    }

    public boolean isEmpty() {
        return sequence.size() == 0;
    }

    public Range asRange() {
        return new Range(start, end);
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
        return regionType + "\t" + start + "\t" + end + "\t" + sequence;
    }

    @Override
    public int compareTo(SequenceRegion<S, E> o) {
        int res = Integer.compare(regionType.ordinal(), o.regionType.ordinal());
        res = res == 0 ? Integer.compare(start, o.start) : res;
        return res == 0 ? Integer.compare(end, o.end) : res;
    }
}
