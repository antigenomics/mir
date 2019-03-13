package com.antigenomics.mir.structure.pdb.geometry.summary;

import com.antigenomics.mir.structure.pdb.Atom;
import com.antigenomics.mir.structure.pdb.Residue;
import com.antigenomics.mir.structure.pdb.geometry.GeometryUtils;

public final class ResidueTorsionAngles {
    private final ResidueBackbone previous, current, next;
    private final double phi, psi, omega;

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

    private static double computeTorsionAngleSafe(Atom a1, Atom a2, Atom a3, Atom a4) {
        if (a1 == null || a2 == null || a3 == null || a4 == null) {
            return Float.NaN;
        }
        return GeometryUtils.torsionAngle(a1.getCoordinates(),
                a2.getCoordinates(),
                a3.getCoordinates(),
                a4.getCoordinates());
    }

    ResidueTorsionAngles(ResidueBackbone previous, ResidueBackbone current, ResidueBackbone next,
                         double phi, double psi, double omega) {
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

    public double getPhi() {
        return phi;
    }

    public double getPsi() {
        return psi;
    }

    public double getOmega() {
        return omega;
    }

    public String asRow() {
        return (float) phi + "\t" + (float) psi + "\t" + (float) omega;
    }

    @Override
    public String toString() {
        return current.getResidue().toShortString() + " [" + (float) (phi * 180 / Math.PI) + ", " +
                (float) (psi * 180 / Math.PI) + ", " +
                (float) (omega * 180 / Math.PI) + "]";
    }
}
