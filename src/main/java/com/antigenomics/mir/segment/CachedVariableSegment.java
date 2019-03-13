package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public final class CachedVariableSegment extends VariableSegmentImpl {
    public CachedVariableSegment(String id, NucleotideSequence germlineNt, int cdr1Start, int cdr1End, int cdr2Start, int cdr2End, int referencePoint, boolean majorAllele) {
        super(id, germlineNt, cdr1Start, cdr1End, cdr2Start, cdr2End, referencePoint, majorAllele);
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
