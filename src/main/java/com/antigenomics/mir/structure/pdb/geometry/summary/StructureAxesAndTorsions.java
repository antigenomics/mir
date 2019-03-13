package com.antigenomics.mir.structure.pdb.geometry.summary;

import com.antigenomics.mir.structure.pdb.Chain;
import com.antigenomics.mir.structure.pdb.Structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class StructureAxesAndTorsions {
    private final Map<Character, ChainPrincipalAxes> principalAxesByChain = new HashMap<>();
    private final Map<Character, ChainTorsionAngles> torsionAnglesByChain = new HashMap<>();
    private final Structure structure;

    public StructureAxesAndTorsions(Structure structure) {
        this.structure = structure;

        for (Chain chain : structure.getChains()) {
            principalAxesByChain.put(chain.getChainIdentifier(), new ChainPrincipalAxes(chain));
            torsionAnglesByChain.put(chain.getChainIdentifier(), new ChainTorsionAngles(chain));
        }
    }

    public Structure getStructure() {
        return structure;
    }

    public Map<Character, ChainPrincipalAxes> getPrincipalAxesByChain() {
        return Collections.unmodifiableMap(principalAxesByChain);
    }

    public Map<Character, ChainTorsionAngles> getTorsionAnglesByChain() {
        return Collections.unmodifiableMap(torsionAnglesByChain);
    }
}
