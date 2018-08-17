package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.Cdr3GermlineSegment;

public interface SegmentGenerator<T extends Cdr3GermlineSegment> {
    T generate();
}
