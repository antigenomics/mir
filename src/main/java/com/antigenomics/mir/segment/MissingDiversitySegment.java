package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

public final class MissingDiversitySegment implements DiversitySegment {
    public static MissingDiversitySegment INSTANCE = new MissingDiversitySegment();

    private MissingDiversitySegment() {
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize5, int trimmingSize3) {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getCdr3Part() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getCdr3PartWithP() {
        return NucleotideSequence.EMPTY;
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
    public String getId() {
        return "D-MISSING";
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
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
