package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.Structure;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class StructurePairDistances {
    private final Structure structure;
    private final Map<String, ChainPairDistances> chainPairDistances = new HashMap<>();
    private final float maxCaDistance, maxAtomDistance;
    private final boolean allowSelf;

    public StructurePairDistances(Structure structure) {
        this(structure, false, 30f, 6f);
    }

    public StructurePairDistances(Structure structure, boolean allowSelf,
                                  float maxCaDistance, float maxAtomDistance) {
        this.structure = structure;
        this.maxCaDistance = maxCaDistance;
        this.maxAtomDistance = maxAtomDistance;
        this.allowSelf = allowSelf;

        for (Chain chain1 : structure) {
            for (Chain chain2 : structure) {
                if (chain1.getChainIdentifier() < chain2.getChainIdentifier() ||
                        (allowSelf && chain1.getChainIdentifier() == chain2.getChainIdentifier())
                ) {
                    chainPairDistances.put(getKey(chain1.getChainIdentifier(), chain2.getChainIdentifier()),
                            new ChainPairDistances(chain1, chain2,
                                    maxCaDistance, maxAtomDistance));
                }
            }
        }
    }

    private static String getKey(Character chainId1, Character chainId2) {
        return chainId1 + " " + chainId2;
    }

    public ChainPairDistances get(Character chainId1, Character chainId2) {
        if (chainId1 > chainId2) {
            return get(chainId2, chainId1);
        }
        if (chainId1 == chainId2 && !allowSelf) {
            throw new IllegalArgumentException("Cannot get self distances - they were not computed");
        }
        return chainPairDistances.get(getKey(chainId1, chainId2));
    }

    public Collection<ChainPairDistances> getChainPairDistances() {
        return Collections.unmodifiableCollection(chainPairDistances.values());
    }

    public Structure getStructure() {
        return structure;
    }

    public float getMaxCaDistance() {
        return maxCaDistance;
    }

    public float getMaxAtomDistance() {
        return maxAtomDistance;
    }

    public boolean isAllowSelf() {
        return allowSelf;
    }
}
