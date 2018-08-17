package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Misc;

public class JoiningSegmentImpl implements JoiningSegment {
    private final String id;
    private final NucleotideSequence cdr3Part, cdr3PartWithP;

    public JoiningSegmentImpl(String id,
                              NucleotideSequence cdr3Part) {
        this.id = id;
        this.cdr3Part = cdr3Part;
        this.cdr3PartWithP = cdr3Part.getReverseComplement().concatenate(cdr3Part);
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        int size = cdr3Part.size();
        return Misc.getSequenceRangeSafe(cdr3PartWithP,
                size + trimmingSize, 2 * size);
    }

    @Override
    public NucleotideSequence getCdr3Part() {
        return cdr3Part;
    }

    @Override
    public NucleotideSequence getCdr3PartWithP() {
        return cdr3Part;
    }

    @Override
    public String getId() {
        return id;
    }
}
