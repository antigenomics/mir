package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public final class CachedDiversitySegment extends DiversitySegmentImpl {
    public CachedDiversitySegment(String id, NucleotideSequence cdr3Part, boolean majorAllele) {
        super(id, cdr3Part, majorAllele);
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
