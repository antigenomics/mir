package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface Segment {
    String getId();

    NucleotideSequence getGermlineSequence();

    boolean isMissingInLibrary();

    boolean isMajorAllele();
}
