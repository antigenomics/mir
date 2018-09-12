package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;

public class DiversitySegmentImpl implements DiversitySegment {
    private final String id;
    private final NucleotideSequence cdr3Part, cdr3PartWithP;
    private final AminoAcidSequence cdr3PartAa;
    private final boolean majorAllele;

    public DiversitySegmentImpl(String id,
                                NucleotideSequence cdr3Part,
                                boolean majorAllele) {
        this.id = id;
        this.cdr3Part = cdr3Part;
        this.cdr3PartAa = AminoAcidSequence.translateFromLeft(cdr3Part);
        this.majorAllele = majorAllele;
        NucleotideSequence rc = cdr3Part.getReverseComplement();
        this.cdr3PartWithP = SequenceUtils.getReverse(rc).concatenate(cdr3Part.concatenate(rc));
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize5, int trimmingSize3) {
        int size = cdr3Part.size();
        return SequenceUtils.getSequenceRangeSafe(cdr3PartWithP,
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

    @Override
    public boolean isMissingInLibrary() {
        return false;
    }

    @Override
    public boolean isMajorAllele() {
        return majorAllele;
    }


    @Override
    public NucleotideSequence getGermlineSequenceNt() {
        return cdr3Part;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return cdr3PartAa;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }
}
