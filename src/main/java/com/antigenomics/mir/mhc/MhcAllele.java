package com.antigenomics.mir.mhc;

import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.structure.MhcRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;

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
