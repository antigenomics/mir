package com.milaboratory.mir.model.gen;

import com.milaboratory.mir.segment.SegmentId;

public interface ConditionalSegmentGenerator {
    SegmentId generate(SegmentId segmentId);
}
