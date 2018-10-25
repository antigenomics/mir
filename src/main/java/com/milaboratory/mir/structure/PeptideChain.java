package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.pdb.Chain;

import java.util.EnumMap;

public final class PeptideChain implements StructureChainWithMarkup<PeptideRegionType> {
    private final AminoAcidSequence sequence;
    private final Chain structureChain;
    private final SequenceRegionMarkup<AminoAcidSequence, PeptideRegionType, ? extends SequenceRegionMarkup> markup;

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
}
