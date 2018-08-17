package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Misc;

public class VariableSegmentImpl implements VariableSegment {
    private final String id;
    private final NucleotideSequence germline,
            cdr3Part, cdr3PartWithP;
    private final int cdr1Start, cdr1End, cdr2Start, cdr2End, referencePoint;

    // todo: store cdr1-2 nt&aa

    public VariableSegmentImpl(String id,
                               NucleotideSequence germline,
                               int cdr1Start, int cdr1End, int cdr2Start, int cdr2End, int referencePoint) {
        this.id = id;
        this.germline = germline;
        this.cdr1Start = cdr1Start;
        this.cdr1End = cdr1End;
        this.cdr2Start = cdr2Start;
        this.cdr2End = cdr2End;
        this.referencePoint = referencePoint;

        this.cdr3Part = germline.getRange(0,
                referencePoint - 3); // V reference point = 0-based coord of point directly after Cys codon
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

    @Override
    public NucleotideSequence getFullGermline() {
        return germline;
    }

    public int getCdr1Start() {
        return cdr1Start;
    }

    public int getCdr1End() {
        return cdr1End;
    }

    public int getCdr2Start() {
        return cdr2Start;
    }

    public int getCdr2End() {
        return cdr2End;
    }

    public int getReferencePoint() {
        return referencePoint;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }
}
