package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public class MissingDiversitySegment  implements DiversitySegment {
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
    public NucleotideSequence getFullGermline() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public String getId() {
        return "D-MISSING";
    }

    @Override
    public boolean isDummy() {
        return true;
    }
}
