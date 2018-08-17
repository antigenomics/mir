package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface VariableSegment extends Cdr3GermlineSegment {
    NucleotideSequence getTrimmedCdr3Part(int trimmingSize);
}
