package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface DiversitySegment extends Cdr3GermlineSegment {
    NucleotideSequence getTrimmedCdr3Part(int trimmingSize5, int trimmingSize3);

    default SegmentType getSegmentType() {
        return SegmentType.D;
    }
}
