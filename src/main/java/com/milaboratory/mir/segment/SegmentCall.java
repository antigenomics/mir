package com.milaboratory.mir.segment;

public class SegmentCall<T extends Segment> {
    private final T segment;
    private final float weight;

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
}
