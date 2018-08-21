package com.milaboratory.mir.segment.provider;

import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableSegmentProvider implements SegmentProvider<VariableSegment> {
    private final SegmentLibrary segmentLibrary;

    public VariableSegmentProvider(SegmentLibrary segmentLibrary) {
        this.segmentLibrary = segmentLibrary;
    }

    @Override
    public VariableSegment fromId(String id) {
        return segmentLibrary.getOrCreateV(id);
    }
}
