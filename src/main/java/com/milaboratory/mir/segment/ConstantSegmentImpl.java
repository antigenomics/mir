package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public class ConstantSegmentImpl implements ConstantSegment {
    private final String id;
    private final NucleotideSequence sequence;

    public ConstantSegmentImpl(String id, NucleotideSequence sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isDummy() {
        return false;
    }

    @Override
    public NucleotideSequence getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return id;
    }
}
