package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;

public final class MhcAllele {
    private final String id;
    private final MhcChainType mhcChain;
    private final MhcClassType mhcClass;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> regionMarkup;

    public MhcAllele(String id,
                     MhcChainType mhcChain, MhcClassType mhcClass,
                     SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> regionMarkup) {
        this.id = id;
        this.mhcChain = mhcChain;
        this.mhcClass = mhcClass;
        this.regionMarkup = regionMarkup.asPrecomputed();
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

    public PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> getRegionMarkup() {
        return regionMarkup;
    }

    @Override
    public String toString() {
        return id + "\t" + regionMarkup.toString();
    }
}
