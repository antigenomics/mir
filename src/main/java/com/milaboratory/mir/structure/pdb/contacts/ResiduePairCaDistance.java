package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.geometry.GeometryUtils;
import com.milaboratory.mir.structure.pdb.geometry.summary.ResidueBackbone;

public final class ResiduePairCaDistance {
    private final Residue residue1, residue2;
    private final double caDistance;

    public ResiduePairCaDistance(Residue residue1, Residue residue2, float caDistance) {
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.caDistance = caDistance;
    }

    public ResiduePairCaDistance(Residue residue1, Residue residue2) {
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.caDistance = caDistanceSafe(residue1, residue2);
    }

    private static double caDistanceSafe(Residue residue1, Residue residue2) {
        var ca1 = new ResidueBackbone(residue1).getCA();
        var ca2 = new ResidueBackbone(residue2).getCA();

        if (ca1 == null || ca2 == null) {
            return Float.NaN;
        }

        return GeometryUtils.distance(ca1.getCoordinates(), ca2.getCoordinates());
    }

    public boolean passesThreshold(float maxDist) {
        return caDistance <= maxDist;
    }

    public ResiduePairDistances computeAtomDistances() {
        return new ResiduePairDistances(this);
    }

    public Residue getResidue1() {
        return residue1;
    }

    public Residue getResidue2() {
        return residue2;
    }

    public double getCaDistance() {
        return caDistance;
    }
}
