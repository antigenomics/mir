package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public class MissingConstantSegment implements ConstantSegment {
    public static MissingConstantSegment INSTANCE = new MissingConstantSegment();

    private MissingConstantSegment() {
    }

    @Override
    public String getId() {
        return "C-MISSING";
    }

    @Override
    public boolean isDummy() {
        return true;
    }

    @Override
    public NucleotideSequence getSequence() {
        return NucleotideSequence.EMPTY;
    }
}
