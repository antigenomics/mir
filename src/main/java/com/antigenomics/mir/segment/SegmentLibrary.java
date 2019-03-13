package com.antigenomics.mir.segment;

import java.util.Collection;

public interface SegmentLibrary {
    VariableSegment getV(String id);

    DiversitySegment getD(String id);

    JoiningSegment getJ(String id);

    ConstantSegment getC(String id);


    VariableSegment getVMajor(String id);

    DiversitySegment getDMajor(String id);

    JoiningSegment getJMajor(String id);

    ConstantSegment getCMajor(String id);


    Collection<VariableSegment> getAllV();

    Collection<DiversitySegment> getAllD();

    Collection<JoiningSegment> getAllJ();

    Collection<ConstantSegment> getAllC();


    Collection<VariableSegment> getAllVMajor();

    Collection<DiversitySegment> getAllDMajor();

    Collection<JoiningSegment> getAllJMajor();

    Collection<ConstantSegment> getAllCMajor();


    Species getSpecies();

    Gene getGene();


    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentClass) {
        return asSegmentProvider(segmentClass, false);
    }

    @SuppressWarnings("unchecked")
    default <T extends Segment> SegmentProvider<T> asSegmentProvider(Class<T> segmentClass,
                                                                     boolean major) {
        if (segmentClass.equals(VariableSegment.class)) {
            return major ? id -> (T) this.getVMajor(id) : id -> (T) this.getV(id);
        } else if (segmentClass.equals(JoiningSegment.class)) {
            return major ? id -> (T) this.getJMajor(id) : id -> (T) this.getJ(id);
        } else if (segmentClass.equals(DiversitySegment.class)) {
            return major ? id -> (T) this.getDMajor(id) : id -> (T) this.getD(id);
        } else if (segmentClass.equals(ConstantSegment.class)) {
            return major ? id -> (T) this.getCMajor(id) : id -> (T) this.getC(id);
        }
        throw new IllegalArgumentException("Unknown segment type " + segmentClass);
    }
}