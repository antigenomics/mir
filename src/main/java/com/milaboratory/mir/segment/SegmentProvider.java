package com.milaboratory.mir.segment;

@FunctionalInterface
public interface SegmentProvider<T extends Segment> {
    T fromId(String id);
}
