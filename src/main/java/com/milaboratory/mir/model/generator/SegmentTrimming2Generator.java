package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.DiversitySegment;

public interface SegmentTrimming2Generator<T extends DiversitySegment> {
    TrimmingPair generate(T segmentId);
}
