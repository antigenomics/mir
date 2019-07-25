package com.antigenomics.mir.mhc;

import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.structure.MhcRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;

public final class MhcAlleleWithSequence extends MhcAlleleImpl {
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> regionMarkup;

    public MhcAlleleWithSequence(String id,
                                 MhcChainType mhcChainType, MhcClassType mhcClassType, Species species,
                                 SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> regionMarkup) {
        super(id, mhcChainType, mhcClassType, species);
        this.regionMarkup = regionMarkup.asPrecomputed();
    }

    public PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> getRegionMarkup() {
        return regionMarkup;
    }

    @Override
    public String toString() {
        return getId() + "\t" + regionMarkup.toString();
    }
}
