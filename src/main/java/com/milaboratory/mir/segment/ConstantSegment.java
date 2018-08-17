package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface ConstantSegment extends Segment {
    NucleotideSequence getSequence();
}
