package com.milaboratory.mir.segment.provider;

import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentLibrary;

public class JoiningSegmentProvider implements SegmentProvider<JoiningSegment> {
    private final SegmentLibrary segmentLibrary;

    public JoiningSegmentProvider(SegmentLibrary segmentLibrary) {
        this.segmentLibrary = segmentLibrary;
    }

    @Override
    public JoiningSegment fromId(String id) {
        return segmentLibrary.getOrCreateJ(id);
    }
}
