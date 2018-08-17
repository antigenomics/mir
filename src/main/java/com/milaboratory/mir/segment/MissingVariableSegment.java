package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public class MissingVariableSegment implements VariableSegment {
    public static MissingVariableSegment INSTANCE = new MissingVariableSegment();

    private MissingVariableSegment() {
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
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
        return "V-MISSING";
    }
}
