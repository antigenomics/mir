package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Misc;

public class DiversitySegmentImpl implements DiversitySegment{
    private final String id;
    private final NucleotideSequence cdr3Part, cdr3PartWithP;

    public DiversitySegmentImpl(String id,
                              NucleotideSequence cdr3Part) {
        this.id = id;
        this.cdr3Part = cdr3Part;
        NucleotideSequence rc = cdr3Part.getReverseComplement();
        this.cdr3PartWithP = Misc.getReverse(rc).concatenate(cdr3Part.concatenate(rc));
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize5, int trimmingSize3) {
        int size = cdr3Part.size();
        return Misc.getSequenceRangeSafe(cdr3PartWithP,
                size + trimmingSize5, 2 * size - trimmingSize3);
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
