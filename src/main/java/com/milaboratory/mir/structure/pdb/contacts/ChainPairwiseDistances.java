package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.Residue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainPairwiseDistances<C1 extends Chain, C2 extends Chain> {
    protected final C1 chain1;
    protected final C2 chain2;
    private final float maxCaDistance, maxAtomDistance;
    private final List<ResiduePairAtomDistances> residuePairAtomDistances = new ArrayList<>();

    public ChainPairwiseDistances(C1 chain1, C2 chain2) {
        this(chain1, chain2, 25f, 5f);
    }

    public ChainPairwiseDistances(C1 chain1, C2 chain2, float maxCaDistance, float maxAtomDistance) {
        this.chain1 = chain1;
        this.chain2 = chain2;
        this.maxCaDistance = maxCaDistance;
        this.maxAtomDistance = maxAtomDistance;

        for (Residue residue1 : chain1) {
            for (Residue residue2 : chain2) {
                var caDist = new ResiduePairCaDistance(residue1, residue2);
                if (maxCaDistance < 0 || caDist.passesThreshold(maxCaDistance)) {
                    var atomDistances = caDist.computeAtomDistances();
                    this.residuePairAtomDistances.add(atomDistances.filter(maxAtomDistance));
                }
            }
        }
    }

    public C1 getChain1() {
        return chain1;
    }

    public C2 getChain2() {
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
}
