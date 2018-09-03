package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;

public class JoiningSegmentImpl implements JoiningSegment {
    private final String id;
    private final NucleotideSequence germline, cdr3Part, cdr3PartWithP;
    private final int referencePoint;

    public JoiningSegmentImpl(String id,
                              NucleotideSequence germline,
                              int referencePoint) {
        this.id = id;
        this.germline = germline;
        this.referencePoint = referencePoint;
        this.cdr3Part = germline.getRange(0,
                referencePoint + 4); // J reference point is the 0-base coordinate of first base before Phe/Trp
        this.cdr3PartWithP = cdr3Part.getReverseComplement().concatenate(cdr3Part);
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        int size = cdr3Part.size();
        return SequenceUtils.getSequenceRangeSafe(cdr3PartWithP,
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

    @Override
    public boolean isDummy() {
        return false;
    }

    @Override
    public NucleotideSequence getFullGermline() {
        return germline;
    }

    public int getReferencePoint() {
        return referencePoint;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }
}
