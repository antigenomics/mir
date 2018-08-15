package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.SegmentId;

public interface ConditionalSegmentGenerator {
    SegmentId generate(SegmentId segmentId);
}
