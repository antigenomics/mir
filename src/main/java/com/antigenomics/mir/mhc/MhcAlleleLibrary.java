package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

import java.util.Collection;

public interface MhcAlleleLibrary<T extends MhcAllele> {
    T getAllele(String id);

    T getAllele(String id, MhcChainType mhcChainType);

    MhcClassType getMhcClass();

    Species getSpecies();

    Collection<T> getAlleles();
}
