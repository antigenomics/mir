package com.antigenomics.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.structure.pdb.Chain;

public final class PeptideChain implements StructureChainWithMarkup<PeptideRegionType> {
    private final AminoAcidSequence sequence;
    private final Chain structureChain;
    private final SequenceRegionMarkup<AminoAcidSequence, PeptideRegionType, ? extends SequenceRegionMarkup> markup;

    public static final PeptideChain DUMMY = new PeptideChain(AminoAcidSequence.EMPTY, Chain.DUMMY);

    public PeptideChain(AminoAcidSequence sequence, Chain structureChain) {
        this.sequence = sequence;
        this.structureChain = structureChain;
        this.markup = new ArrayBasedSequenceRegionMarkup<>(sequence, new int[]{0, sequence.size()},
                PeptideRegionType.class);
    }

    public AminoAcidSequence getSequence() {
        return sequence;
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, PeptideRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return markup;
    }

    public Chain getStructureChain() {
        return structureChain;
    }

    @Override
    public String getAlleleInfoStr() {
        return sequence.toString();
    }

    @Override
    public String getChainTypeStr() {
        return ComplexComponentType.PEPTIDE.toString();
    }
}
