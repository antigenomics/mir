package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.clonotype.SegmentTrimming;
import com.milaboratory.mir.segment.SegmentId;

public interface RearrangementInfo {
    SegmentId getVSegment();

    SegmentId getJSegment();

    SegmentTrimming getSegmentTrimming();
}
