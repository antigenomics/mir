package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.segment.Cdr3GermlineSegment;

public interface ConditionalSegmentGenerator<T extends Cdr3GermlineSegment, V extends Cdr3GermlineSegment> {
    T generate(V segmentId);
}
