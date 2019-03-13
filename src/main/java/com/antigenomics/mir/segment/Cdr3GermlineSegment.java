package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface Cdr3GermlineSegment extends Segment {
    NucleotideSequence getCdr3Part();

    NucleotideSequence getCdr3PartWithP();
}
