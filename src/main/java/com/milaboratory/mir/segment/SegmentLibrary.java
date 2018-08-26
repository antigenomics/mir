package com.milaboratory.mir.segment;

public interface SegmentLibrary {
    VariableSegment getOrCreateV(String id);

    JoiningSegment getOrCreateJ(String id);

    DiversitySegment getOrCreateD(String id);

    ConstantSegment getOrCreateC(String id);

    Species getSpecies();

    Gene getGene();

    @SuppressWarnings("unchecked")
    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentClass) {
        if (segmentClass.equals(VariableSegment.class)) {
            return id -> (T) this.getOrCreateV(id);
        } else if (segmentClass.equals(JoiningSegment.class)) {
            return id -> (T) this.getOrCreateJ(id);
        } else if (segmentClass.equals(DiversitySegment.class)) {
            return id -> (T) this.getOrCreateD(id);
        } else if (segmentClass.equals(ConstantSegment.class)) {
            return id -> (T) this.getOrCreateC(id);
        }
        throw new IllegalArgumentException("Unknown segment type " + segmentClass);
    }

    @SuppressWarnings("unchecked")
    default SegmentProvider asSegmentProvider(SegmentType segmentType) {
        return asSegmentProvider(segmentType.getTypeClass());
    }
}
