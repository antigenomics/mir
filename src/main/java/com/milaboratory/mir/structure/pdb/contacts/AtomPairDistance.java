package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.geometry.GeometryUtils;

public final class AtomPairDistance {
    private final Atom atom1, atom2;
    private final float distance;

    public AtomPairDistance(Atom atom1, Atom atom2) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        this.distance = GeometryUtils.distance(atom1.getCoordinates(), atom2.getCoordinates());
    }

    public Atom getAtom1() {
        return atom1;
    }

    public Atom getAtom2() {
        return atom2;
    }

    public float getDistance() {
        return distance;
    }
}
