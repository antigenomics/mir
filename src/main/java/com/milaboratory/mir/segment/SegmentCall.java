package com.milaboratory.mir.segment;

import java.util.Collections;
import java.util.List;

public class SegmentCall<T extends Segment> {
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
}
