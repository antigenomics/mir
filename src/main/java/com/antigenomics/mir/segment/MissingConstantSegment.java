package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

public final class MissingConstantSegment implements ConstantSegment {
    public static MissingConstantSegment INSTANCE = new MissingConstantSegment();

    private MissingConstantSegment() {
    }

    @Override
    public String getId() {
        return "C-MISSING";
    }

    @Override
    public boolean isMissingInLibrary() {
        return true;
    }

    @Override
    public boolean isMajorAllele() {
        return true;
    }

    @Override
    public NucleotideSequence getGermlineSequenceNt() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return AminoAcidSequence.EMPTY;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
