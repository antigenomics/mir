package com.antigenomics.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.SequenceUtils;

import java.util.Objects;

public class DiversitySegmentImpl implements DiversitySegment {
    private final String id;
    private final NucleotideSequence cdr3Part, cdr3PartWithP;
    private final AminoAcidSequence cdr3PartAa;
    private final boolean majorAllele;

    public static DiversitySegmentImpl mock(String id) {
        return new DiversitySegmentImpl(id, NucleotideSequence.EMPTY, true);
    }

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
    public NucleotideSequence getGermlineSequenceNtWithoutCdr3() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return cdr3PartAa;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiversitySegmentImpl that = (DiversitySegmentImpl) o;
        return majorAllele == that.majorAllele &&
                Objects.equals(id, that.id) &&
                Objects.equals(cdr3Part, that.cdr3Part);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cdr3Part, majorAllele);
    }
}
