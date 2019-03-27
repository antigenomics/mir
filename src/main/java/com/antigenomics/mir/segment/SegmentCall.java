package com.antigenomics.mir.segment;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.Collections;
import java.util.List;

public class SegmentCall<T extends Segment> implements Comparable<SegmentCall<T>> {
    private final T segment;
    private final float weight;

    public SegmentCall(T segment) {
        this(segment, 1.0f);
    }

    public SegmentCall(T segment, float weight) {
        this.segment = segment;
        this.weight = weight;
    }

    public T getSegment() {
        return segment;
    }

    public float getWeight() {
        return weight;
    }

    public static <T extends Segment> SegmentCall<T> asCall(T segment) {
        return new SegmentCall<>(segment);
    }

    public static <T extends Segment> List<SegmentCall<T>> asCallList(T segment) {
        return Collections.singletonList(asCall(segment));
    }

    @Override
    public String toString() {
        return segment.getId() + "(" + weight + ")";
    }

    @Override
    public int compareTo(SegmentCall<T> o) {
        return Float.compare(weight, o.weight);
    }
}
