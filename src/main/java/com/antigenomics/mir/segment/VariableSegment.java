package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface VariableSegment extends Cdr3GermlineSegment, SegmentWithMarkup {
    NucleotideSequence getTrimmedCdr3Part(int trimmingSize);

    default SegmentType getSegmentType() {
        return SegmentType.V;
    }
}
