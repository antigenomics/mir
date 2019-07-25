package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

public class MhcAlleleImpl implements MhcAllele {
    private final String id;
    private final MhcChainType mhcChainType;
    private final MhcClassType mhcClassType;
    private final Species species;

    public MhcAlleleImpl(String id, MhcChainType mhcChainType, MhcClassType mhcClassType, Species species) {
        this.id = id;
        this.mhcChainType = mhcChainType;
        this.mhcClassType = mhcClassType;
        this.species = species;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public MhcChainType getMhcChainType() {
        return mhcChainType;
    }

    @Override
    public MhcClassType getMhcClassType() {
        return mhcClassType;
    }

    @Override
    public Species getSpecies() {
        return species;
    }
}
