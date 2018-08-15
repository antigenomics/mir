package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.SegmentId;

public interface SegmentTrimming2Generator {
    TrimmingPair generate(SegmentId segmentId);
}
