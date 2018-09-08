package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public class ConstantSegmentImpl implements ConstantSegment {
    private final String id;
    private final NucleotideSequence sequence;
    private final boolean majorAllele;

    public ConstantSegmentImpl(String id, NucleotideSequence sequence, boolean majorAllele) {
        this.id = id;
        this.sequence = sequence;
        this.majorAllele = majorAllele;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isMissingInLibrary() {
        return false;
    }

    @Override
    public boolean isMajorAllele() {
        return majorAllele;
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
