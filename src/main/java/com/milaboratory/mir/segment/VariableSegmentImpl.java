package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Misc;

public class VariableSegmentImpl implements VariableSegment {
    private final String id;
    private final NucleotideSequence cdr3Part, cdr3PartWithP;

    public VariableSegmentImpl(String id,
                               NucleotideSequence cdr3Part) {
        this.id = id;
        this.cdr3Part = cdr3Part;
        this.cdr3PartWithP = cdr3Part.concatenate(cdr3Part.getReverseComplement());
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        return Misc.getSequenceRangeSafe(cdr3PartWithP,
                0, cdr3Part.size() - trimmingSize);
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
