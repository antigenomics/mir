package com.milaboratory.mir.segment;

public interface SegmentLibrary {
    VariableSegment getOrCreateV(String id);

    JoiningSegment getOrCreateJ(String id);

    DiversitySegment getOrCreateD(String id);

    ConstantSegment getOrCreateC(String id);

    Species getSpecies();

    Gene getGene();
}
