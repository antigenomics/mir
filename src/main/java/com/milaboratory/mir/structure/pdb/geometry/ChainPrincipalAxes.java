package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Chain;

public final class ChainPrincipalAxes {
    private final Chain chain;
    private final Coordinates centerOfMass, principalAxis1, principalAxis2, principalAxis3;

    public ChainPrincipalAxes(Chain chain) {
        this.chain = chain;
        // todo: maybe we can optimize?
        this.centerOfMass = MechanicsUtils.centerOfMass(chain);
        var pa = MechanicsUtils.principalAxes(chain).asVectors();
        this.principalAxis1 = pa[0];
        this.principalAxis2 = pa[1];
        this.principalAxis3 = pa[2];
    }

    ChainPrincipalAxes(Chain chain, Coordinates centerOfMass,
                       Coordinates principalAxis1, Coordinates principalAxis2, Coordinates principalAxis3) {
        this.centerOfMass = centerOfMass;
        this.principalAxis1 = principalAxis1;
        this.principalAxis2 = principalAxis2;
        this.principalAxis3 = principalAxis3;
        this.chain = chain;
    }

    public Chain getChain() {
        return chain;
    }

    public Coordinates getCenterOfMass() {
        return centerOfMass;
    }

    public Coordinates getPrincipalAxis1() {
        return principalAxis1;
    }

    public Coordinates getPrincipalAxis2() {
        return principalAxis2;
    }

    public Coordinates getPrincipalAxis3() {
        return principalAxis3;
    }

    @Override
    public String toString() {
        return chain.getChainIdentifier() + ":\n" +
                "cm\t" + centerOfMass + "\n" +
                "pa1\t" + principalAxis1 + "\n" +
                "pa2\t" + principalAxis2 + "\n" +
                "pa3\t" + principalAxis3;
    }
}
