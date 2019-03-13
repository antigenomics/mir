package com.antigenomics.mir.structure.pdb.contacts;

import com.antigenomics.mir.structure.pdb.Residue;
import com.antigenomics.mir.structure.pdb.geometry.GeometryUtils;
import com.antigenomics.mir.structure.pdb.geometry.summary.ResidueBackbone;

public class ResiduePairCaDistance implements ResiduePairDistance {
    protected final Residue residue1, residue2;
    protected final double caDistance;

    protected ResiduePairCaDistance(Residue residue1, Residue residue2,
                                    double caDistance) {
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
            return Double.NaN;
        }

        return GeometryUtils.distance(ca1.getCoordinates(), ca2.getCoordinates());
    }

    public ResiduePairAtomDistances computeAtomDistances() {
        return new ResiduePairAtomDistances(this);
    }

    @Override
    public Residue getResidue1() {
        return residue1;
    }

    @Override
    public Residue getResidue2() {
        return residue2;
    }

    @Override
    public double getDistance() {
        return caDistance;
    }

    public double getCaDistance() {
        return caDistance;
    }
}
