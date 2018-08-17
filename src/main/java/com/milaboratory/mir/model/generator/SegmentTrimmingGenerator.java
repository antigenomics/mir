package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.Cdr3GermlineSegment;

public interface SegmentTrimmingGenerator<T extends Cdr3GermlineSegment> {
    int generate(T segmentId);
}
