package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

public interface MhcAllele {
    String getId();

    MhcChainType getMhcChainType();

    MhcClassType getMhcClassType();

    Species getSpecies();
}
