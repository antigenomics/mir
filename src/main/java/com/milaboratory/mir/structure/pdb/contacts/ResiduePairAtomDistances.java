package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.parser.AtomType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ResiduePairAtomDistances implements ResiduePairDistance{
    private final List<AtomPairDistance> atomDistances;
    private final Residue residue1, residue2;
    private final double minAtomDistance;

    private ResiduePairAtomDistances(List<AtomPairDistance> atomDistances,
                                     Residue residue1, Residue residue2, double minAtomDistance) {
        this.atomDistances = atomDistances;
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.minAtomDistance = minAtomDistance;
    }

    public ResiduePairAtomDistances(ResiduePairCaDistance residuePairCaDistance) {
        this(residuePairCaDistance.getResidue1(), residuePairCaDistance.getResidue2());
    }

    public ResiduePairAtomDistances(Residue residue1, Residue residue2) {
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.atomDistances = new ArrayList<>();
        double minDist = Double.POSITIVE_INFINITY;

        for (Atom atom1 : residue1) {
            if (atom1.getAtomType() == AtomType.ATOM) {
                for (Atom atom2 : residue2) {
                    if (atom2.getAtomType() == AtomType.ATOM) {
                        var dist = new AtomPairDistance(atom1, atom2);
                        minDist = Math.min(minDist, dist.getDistance());
                        atomDistances.add(dist);
                    }
                }
            }
        }

        this.minAtomDistance = minDist;
    }

    public ResiduePairAtomDistances filter(double maxDist) {
        return new ResiduePairAtomDistances(
                atomDistances.stream().filter(x -> x.getDistance() <= maxDist).collect(Collectors.toList()),
                residue1, residue2, minAtomDistance
        );
    }

    public List<AtomPairDistance> getAtomDistances() {
        return Collections.unmodifiableList(atomDistances);
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
        return minAtomDistance;
    }

    public double getMinAtomDistance() {
        return minAtomDistance;
    }
}
