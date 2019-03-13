package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public final class CachedConstantSegment extends ConstantSegmentImpl {

    public CachedConstantSegment(String id, NucleotideSequence germlineSequenceNt, boolean majorAllele) {
        super(id, germlineSequenceNt, majorAllele);
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
