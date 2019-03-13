package com.antigenomics.mir.structure;

import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.structure.pdb.Chain;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mhc.MhcAllele;

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

    @Override
    public String getChainTypeStr() {
        return mhcAllele.getMhcChainType().toString();
    }

    @Override
    public String getAlleleInfoStr() {
        return mhcAllele.getId();
    }
}
