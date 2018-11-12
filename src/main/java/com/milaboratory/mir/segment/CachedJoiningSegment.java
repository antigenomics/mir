package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public final class CachedJoiningSegment extends JoiningSegmentImpl {
    public CachedJoiningSegment(String id, NucleotideSequence germlineNt, int referencePoint, boolean majorAllele) {
        super(id, germlineNt, referencePoint, majorAllele);
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
