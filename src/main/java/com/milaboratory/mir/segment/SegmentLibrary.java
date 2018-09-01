package com.milaboratory.mir.segment;

public interface SegmentLibrary {
    VariableSegment getOrCreateV(String id);

    JoiningSegment getOrCreateJ(String id);

    DiversitySegment getOrCreateD(String id);

    ConstantSegment getOrCreateC(String id);

    default VariableSegment getV(String id) {
        var segment = getOrCreateV(id);
        if (segment == MissingVariableSegment.INSTANCE) {
            throw new IllegalArgumentException("Segment id '" + id + "' not found in library.");
        }
        return segment;
    }

    default DiversitySegment getD(String id) {
        var segment = getOrCreateD(id);
        if (segment == MissingDiversitySegment.INSTANCE) {
            throw new IllegalArgumentException("Segment id '" + id + "' not found in library.");
        }
        return segment;
    }

    default JoiningSegment getJ(String id) {
        var segment = getOrCreateJ(id);
        if (segment == MissingJoiningSegment.INSTANCE) {
            throw new IllegalArgumentException("Segment id '" + id + "' not found in library.");
        }
        return segment;
    }

    default ConstantSegment getC(String id) {
        var segment = getOrCreateC(id);
        if (segment == MissingConstantSegment.INSTANCE) {
            throw new IllegalArgumentException("Segment id '" + id + "' not found in library.");
        }
        return segment;
    }

    Species getSpecies();

    Gene getGene();

    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentClass) {
        return asSegmentProvider(segmentClass, false);
    }

    @SuppressWarnings("unchecked")
    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentClass,
                                                                     boolean strict) {
        if (segmentClass.equals(VariableSegment.class)) {
            return strict ? id -> (T) this.getV(id) : id -> (T) this.getOrCreateV(id);
        } else if (segmentClass.equals(JoiningSegment.class)) {
            return strict ? id -> (T) this.getJ(id) : id -> (T) this.getOrCreateJ(id);
        } else if (segmentClass.equals(DiversitySegment.class)) {
            return strict ? id -> (T) this.getD(id) : id -> (T) this.getOrCreateD(id);
        } else if (segmentClass.equals(ConstantSegment.class)) {
            return strict ? id -> (T) this.getC(id) : id -> (T) this.getOrCreateC(id);
        }
        throw new IllegalArgumentException("Unknown segment type " + segmentClass);
    }
}
