package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public class MissingVariableSegment implements VariableSegment {
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
    public NucleotideSequence getGermlineSequence() {
        return NucleotideSequence.EMPTY;
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
    public SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa() {
        return PrecomputedSequenceRegionMarkup.empty(AminoAcidSequence.ALPHABET, AntigenReceptorRegionType.class);
    }

    @Override
    public SequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt() {
        return PrecomputedSequenceRegionMarkup.empty(NucleotideSequence.ALPHABET, AntigenReceptorRegionType.class);
    }
}
