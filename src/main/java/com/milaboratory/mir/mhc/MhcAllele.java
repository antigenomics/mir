package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;

public final class MhcAllele {
    private final String id;
    private final MhcChainType mhcChain;
    private final MhcClassType mhcClass;
    private final SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> regionMarkup;

    public MhcAllele(String id,
                     MhcChainType mhcChain, MhcClassType mhcClass,
                     SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> regionMarkup) {
        this.id = id;
        this.mhcChain = mhcChain;
        this.mhcClass = mhcClass;
        this.regionMarkup = regionMarkup;
    }

    public String getId() {
        return id;
    }

    public MhcChainType getMhcChain() {
        return mhcChain;
    }

    public MhcClassType getMhcClass() {
        return mhcClass;
    }

    public SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> getRegionMarkup() {
        return regionMarkup;
    }
}
