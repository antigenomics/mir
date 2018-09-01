package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public final class AbsentDiversitySegment implements DiversitySegment {
    public static AbsentDiversitySegment INSTANCE = new AbsentDiversitySegment();

    private AbsentDiversitySegment() {
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
    public NucleotideSequence getFullGermline() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public String getId() {
        return "D-ABSENT";
    }

    @Override
    public boolean isDummy() {
        return false;
    }
}
