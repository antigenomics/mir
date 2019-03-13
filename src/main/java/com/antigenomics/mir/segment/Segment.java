package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

public interface Segment {
    String getId();

    NucleotideSequence getGermlineSequenceNt();

    AminoAcidSequence getGermlineSequenceAa();

    boolean isMissingInLibrary();

    boolean isMajorAllele();

    // TODO: non-functional alleles

    SegmentType getSegmentType();
}
