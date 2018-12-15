package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.Residue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChainPairwiseDistances {
    private final Chain chain1, chain2;
    private final float maxCaDistance, maxAtomDistance;
    private final List<ResiduePairAtomDistances> residuePairAtomDistances = new ArrayList<>();
    private final List<ResiduePairCaDistance> residuePairCaDistances = new ArrayList<>();

    public ChainPairwiseDistances(Chain chain1, Chain chain2) {
        this(chain1, chain2, 25f, 6f);
    }

    public ChainPairwiseDistances(Chain chain1, Chain chain2, float maxCaDistance, float maxAtomDistance) {
        this.chain1 = chain1;
        this.chain2 = chain2;
        this.maxCaDistance = maxCaDistance;
        this.maxAtomDistance = maxAtomDistance;

        for (Residue residue1 : chain1) {
            for (Residue residue2 : chain2) {
                var caDist = new ResiduePairCaDistance(residue1, residue2);
                if (caDist.passesThreshold(maxCaDistance)) {
                    residuePairCaDistances.add(caDist);
                    residuePairAtomDistances.add(caDist.computeAtomDistances().filter(maxAtomDistance));
                }
            }
        }
    }

    public Chain getChain1() {
        return chain1;
    }

    public Chain getChain2() {
        return chain2;
    }

    public float getMaxCaDistance() {
        return maxCaDistance;
    }

    public float getMaxAtomDistance() {
        return maxAtomDistance;
    }

    public List<ResiduePairAtomDistances> getResiduePairAtomDistances() {
        return Collections.unmodifiableList(residuePairAtomDistances);
    }

    public List<ResiduePairCaDistance> getResiduePairCaDistances() {
        return Collections.unmodifiableList(residuePairCaDistances);
    }
}
