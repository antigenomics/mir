package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

public class ConstantSegmentImpl implements ConstantSegment {
    private final String id;
    private final NucleotideSequence germlineSequenceNt;
    private final AminoAcidSequence germlineSequenceAa;
    private final boolean majorAllele;

    public ConstantSegmentImpl(String id, NucleotideSequence germlineSequenceNt, boolean majorAllele) {
        this.id = id;
        this.germlineSequenceNt = germlineSequenceNt;
        this.germlineSequenceAa = AminoAcidSequence.translateFromLeft(germlineSequenceNt);
        this.majorAllele = majorAllele;
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
        return germlineSequenceNt;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return germlineSequenceAa;
    }

    @Override
    public String toString() {
        return id;
    }
}
