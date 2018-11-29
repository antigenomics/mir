package com.milaboratory.mir.structure.pdb.geometry.summary;

import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.geometry.Vector3;

public final class ChainPrincipalAxes {
    private final Chain chain;
    private final PrincipalAxes principalAxes;

    public ChainPrincipalAxes(Chain chain) {
        this.chain = chain;
        this.principalAxes = new PrincipalAxes(new InertiaTensor(chain));
    }

    public Chain getChain() {
        return chain;
    }

    public PrincipalAxes getPrincipalAxes() {
        return principalAxes;
    }

    @Override
    public String toString() {
        return "Chain " + chain.getChainIdentifier() + ":\n" + principalAxes.toString();
    }
}
