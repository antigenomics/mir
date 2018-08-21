package com.milaboratory.mir.segment.provider;

import com.milaboratory.mir.segment.Segment;

public interface SegmentProvider<T extends Segment> {
    T fromId(String id);
}
