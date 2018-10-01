package com.milaboratory.mir.segment;

public interface ConstantSegment extends Segment {
    default SegmentType getSegmentType() {
        return SegmentType.C;
    }
}
