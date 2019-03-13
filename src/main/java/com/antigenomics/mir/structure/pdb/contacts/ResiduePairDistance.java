package com.antigenomics.mir.structure.pdb.contacts;

import com.antigenomics.mir.structure.pdb.Residue;

public interface ResiduePairDistance {
    Residue getResidue1();

    Residue getResidue2();

    double getDistance();

    default boolean passesThreshold(float maxDist) {
        return getDistance() <= maxDist;
    }
}
