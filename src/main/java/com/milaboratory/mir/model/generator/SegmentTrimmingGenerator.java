package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.SegmentId;

public interface SegmentTrimmingGenerator {
    int generate(SegmentId segmentId);
}
