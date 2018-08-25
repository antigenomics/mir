package com.milaboratory.mir.rearrangement.generator;

import com.milaboratory.mir.segment.Cdr3GermlineSegment;

public interface ConditionalSegmentGenerator<T extends Cdr3GermlineSegment, V extends Cdr3GermlineSegment> {
    T generate(V segmentId);
    double getProbability(T segment, V conditionalSegment);
}
