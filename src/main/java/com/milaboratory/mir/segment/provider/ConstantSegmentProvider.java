package com.milaboratory.mir.segment.provider;

import com.milaboratory.mir.segment.ConstantSegment;
import com.milaboratory.mir.segment.SegmentLibrary;

public class ConstantSegmentProvider implements SegmentProvider<ConstantSegment> {
    private final SegmentLibrary segmentLibrary;

    public ConstantSegmentProvider(SegmentLibrary segmentLibrary) {
        this.segmentLibrary = segmentLibrary;
    }

    @Override
    public ConstantSegment fromId(String id) {
        return segmentLibrary.getOrCreateC(id);
    }
}
