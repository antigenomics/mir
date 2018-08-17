package com.milaboratory.mir.segment;

import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;

public interface SegmentLibrary {
    VariableSegment getOrCreateV(String id);

    JoiningSegment getOrCreateJ(String id);

    DiversitySegment getOrCreateD(String id);

    Species getSpecies();

    Gene getGene();
}
