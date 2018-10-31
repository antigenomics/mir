package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.MhcRegionType;

public final class MhcAllele {
    private final String id;
    private final MhcChainType mhcChainType;
    private final MhcClassType mhcClassType;
    private final Species species;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> regionMarkup;

    public MhcAllele(String id,
                     MhcChainType mhcChainType, MhcClassType mhcClassType, Species species,
                     SequenceRegionMarkup<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> regionMarkup) {
        this.id = id;
        this.mhcChainType = mhcChainType;
        this.mhcClassType = mhcClassType;
        this.species = species;
        this.regionMarkup = regionMarkup.asPrecomputed();
    }

    public String getId() {
        return id;
    }

    public MhcChainType getMhcChainType() {
        return mhcChainType;
    }

    public MhcClassType getMhcClassType() {
        return mhcClassType;
    }

    public Species getSpecies() {
        return species;
    }

    public PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType> getRegionMarkup() {
        return regionMarkup;
    }

    @Override
    public String toString() {
        return id + "\t" + regionMarkup.toString();
    }
}
