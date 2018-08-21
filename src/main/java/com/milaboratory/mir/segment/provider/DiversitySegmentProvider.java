package com.milaboratory.mir.segment.provider;

import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.SegmentLibrary;

public class DiversitySegmentProvider implements SegmentProvider<DiversitySegment> {
    private final SegmentLibrary segmentLibrary;

    public DiversitySegmentProvider(SegmentLibrary segmentLibrary) {
        this.segmentLibrary = segmentLibrary;
    }

    @Override
    public DiversitySegment fromId(String id) {
        return segmentLibrary.getOrCreateD(id);
    }
}
