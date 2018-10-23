package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mhc.MhcChainType;
import com.milaboratory.mir.mhc.MhcRegionType;
import com.milaboratory.mir.structure.pdb.Chain;

public class MhcChain implements StructureChainWithMarkup<MhcRegionType> {
    private final MhcAllele mhcAllele;
    private final MhcChainType mhcChainType;
    private final Chain structureChain;

    public MhcChain(MhcAllele mhcAllele, MhcChainType mhcChainType, Chain structureChain) {
        this.mhcAllele = mhcAllele;
        this.mhcChainType = mhcChainType;
        this.structureChain = structureChain;
    }

    public MhcAllele getMhcAllele() {
        return mhcAllele;
    }

    public MhcChainType getMhcChainType() {
        return mhcChainType;
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return mhcAllele.getRegionMarkup();
    }

    @Override
    public Chain getStructureChain() {
        return structureChain;
    }
}
