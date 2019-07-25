package com.antigenomics.mir.structure;

import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.mhc.MhcAlleleWithSequence;
import com.antigenomics.mir.structure.pdb.Chain;
import com.milaboratory.core.sequence.AminoAcidSequence;

public final class MhcChain implements StructureChainWithMarkup<MhcRegionType> {
    private final MhcAlleleWithSequence mhcAllele;
    private final Chain structureChain;
    private final SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> markup;

    public MhcChain(MhcAlleleWithSequence mhcAllele,
                    SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> markup,
                    Chain structureChain) {
        this.mhcAllele = mhcAllele;
        this.markup = markup;
        this.structureChain = structureChain;
    }

    public MhcAlleleWithSequence getMhcAllele() {
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
