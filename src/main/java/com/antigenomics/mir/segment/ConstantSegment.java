package com.antigenomics.mir.segment;

public interface ConstantSegment extends Segment {
    default SegmentType getSegmentType() {
        return SegmentType.C;
    }
}
