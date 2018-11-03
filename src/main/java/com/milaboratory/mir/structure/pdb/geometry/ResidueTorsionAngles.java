package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;

public final class ResidueTorsionAngles {
    private final ResidueBackbone previous, current, next;
    private final float phi, psi, omega;

    public ResidueTorsionAngles(Residue previous, Residue current, Residue next) {
        this(new ResidueBackbone(previous), new ResidueBackbone(current), new ResidueBackbone(next));
    }

    public ResidueTorsionAngles(ResidueBackbone previous, ResidueBackbone current, ResidueBackbone next) {
        this(previous, current, next,
                // todo: can be optimized by storing previous t/b vectors
                computeTorsionAngleSafe(
                        previous.getC(),
                        current.getN(),
                        current.getCA(),
                        current.getC()
                ),
                computeTorsionAngleSafe(
                        current.getN(),
                        current.getCA(),
                        current.getC(),
                        next.getN()
                ),
                computeTorsionAngleSafe(
                        current.getCA(),
                        current.getC(),
                        next.getN(),
                        next.getCA()
                ));
    }

    private static float computeTorsionAngleSafe(Atom a1, Atom a2, Atom a3, Atom a4) {
        if (a1 == null || a2 == null || a3 == null || a4 == null) {
            return Float.NaN;
        }
        return GeometryUtils.torsionAngle(a1.getCoordinates(),
                a2.getCoordinates(),
                a3.getCoordinates(),
                a4.getCoordinates());
    }

    ResidueTorsionAngles(ResidueBackbone previous, ResidueBackbone current, ResidueBackbone next,
                         float phi, float psi, float omega) {
        this.previous = previous;
        this.current = current;
        this.next = next;
        this.phi = phi;
        this.psi = psi;
        this.omega = omega;
    }

    public ResidueBackbone getPrevious() {
        return previous;
    }

    public ResidueBackbone getCurrent() {
        return current;
    }

    public ResidueBackbone getNext() {
        return next;
    }

    public float getPhi() {
        return phi;
    }

    public float getPsi() {
        return psi;
    }

    public float getOmega() {
        return omega;
    }

    public String asRow() {
        return phi + "\t" + psi + "\t" + omega;
    }

    @Override
    public String toString() {
        return current.getResidue().toShortString() + "\t" + phi + "\t" + psi + "\t" + omega;
    }
}
