package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public class MissingJoiningSegment implements JoiningSegment {
    public static MissingJoiningSegment INSTANCE = new MissingJoiningSegment();

    private MissingJoiningSegment() {
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
    public AminoAcidSequence getGermlineSequenceAa() {
        return AminoAcidSequence.EMPTY;
    }

    @Override
    public String getId() {
        return "J-MISSING";
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
}
