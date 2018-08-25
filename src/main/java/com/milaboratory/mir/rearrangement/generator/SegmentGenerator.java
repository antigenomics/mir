package com.milaboratory.mir.rearrangement.generator;

import com.milaboratory.mir.segment.Cdr3GermlineSegment;

public interface SegmentGenerator<T extends Cdr3GermlineSegment> {
    T generate();

    double getProbability(T segment);
}
