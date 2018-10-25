package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.structure.pdb.Chain;

public final class MhcChain implements StructureChainWithMarkup<MhcRegionType> {
    private final MhcAllele mhcAllele;
    private final Chain structureChain;
    private final SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> markup;

    public MhcChain(MhcAllele mhcAllele,
                    SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> markup,
                    Chain structureChain) {
        this.mhcAllele = mhcAllele;
        this.markup = markup;
        this.structureChain = structureChain;
    }

    public MhcAllele getMhcAllele() {
        return mhcAllele;
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return markup;
    }

    @Override
    public Chain getStructureChain() {
        return structureChain;
    }
}
