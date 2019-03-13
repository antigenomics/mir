package com.antigenomics.mir.segment;

@FunctionalInterface
public interface SegmentProvider<T extends Segment> {
    T fromId(String id);
}
