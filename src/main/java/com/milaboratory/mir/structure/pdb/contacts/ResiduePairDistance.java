package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Residue;

public interface ResiduePairDistance {
    Residue getResidue1();

    Residue getResidue2();

    double getDistance();

    default boolean passesThreshold(float maxDist) {
        return getDistance() <= maxDist;
    }
}
