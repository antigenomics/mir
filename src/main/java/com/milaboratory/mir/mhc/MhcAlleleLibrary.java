package com.milaboratory.mir.mhc;

import com.milaboratory.mir.segment.Species;

import java.util.Map;

public final class MhcAlleleLibrary {
    private final MhcClassType mhcClass;
    private final Species species;
    private final Map<String, MhcAllele> alleleMap;

    public MhcAlleleLibrary(MhcClassType mhcClass, Species species, Map<String, MhcAllele> alleleMap) {
        this.mhcClass = mhcClass;
        this.species = species;
        this.alleleMap = alleleMap;
    }

    public MhcAllele getAllele(String id) {
        return alleleMap.get(id);
    }

    public MhcClassType getMhcClass() {
        return mhcClass;
    }

    public Species getSpecies() {
        return species;
    }
}
