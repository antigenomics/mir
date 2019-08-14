package com.antigenomics.mir.segment;

import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;

public final class MissingVariableSegment implements VariableSegment {
    public static MissingVariableSegment INSTANCE = new MissingVariableSegment();

    private MissingVariableSegment() {
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getCdr3Part() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getCdr3PartWithP() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getGermlineSequenceNt() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public NucleotideSequence getGermlineSequenceNtWithoutCdr3() {
        return NucleotideSequence.EMPTY;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return AminoAcidSequence.EMPTY;
    }

    @Override
    public String getId() {
        return "V-MISSING";
    }

    @Override
    public boolean isMissingInLibrary() {
        return true;
    }

    @Override
    public boolean isMajorAllele() {
        return true;
    }

    @Override
    public int getReferencePoint() {
        return -1;
    }

    @Override
    public PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa() {
        return PrecomputedSequenceRegionMarkup.empty(AminoAcidSequence.ALPHABET, AntigenReceptorRegionType.class);
    }

    @Override
    public PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt() {
        return PrecomputedSequenceRegionMarkup.empty(NucleotideSequence.ALPHABET, AntigenReceptorRegionType.class);
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
