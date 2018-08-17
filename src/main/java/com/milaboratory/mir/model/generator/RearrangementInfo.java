package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.clonotype.SegmentTrimming;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public interface RearrangementInfo {
    VariableSegment getVSegment();

    JoiningSegment getJSegment();

    SegmentTrimming getSegmentTrimming();
}
