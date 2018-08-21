package com.milaboratory.mir.segment;

import com.milaboratory.mir.segment.provider.*;

public interface SegmentLibrary {
    VariableSegment getOrCreateV(String id);

    JoiningSegment getOrCreateJ(String id);

    DiversitySegment getOrCreateD(String id);

    ConstantSegment getOrCreateC(String id);

    Species getSpecies();

    Gene getGene();

    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentType) {
        if (segmentType.equals(VariableSegment.class)) {
            return (SegmentProvider<T>) new VariableSegmentProvider(this);
        } else if (segmentType.equals(JoiningSegment.class)) {
            return (SegmentProvider<T>) new JoiningSegmentProvider(this);
        } else if (segmentType.equals(DiversitySegment.class)) {
            return (SegmentProvider<T>) new DiversitySegmentProvider(this);
        } else if (segmentType.equals(ConstantSegment.class)) {
            return (SegmentProvider<T>) new ConstantSegmentProvider(this);
        }
        throw new IllegalArgumentException("Unknown segment type " + segmentType);
    }

    default SegmentProvider asSegmentProvider(SegmentType segmentType) {
        return asSegmentProvider(segmentType.getTypeClass());
    }
}
