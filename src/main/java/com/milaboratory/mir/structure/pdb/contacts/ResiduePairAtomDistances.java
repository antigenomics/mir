package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.parser.AtomType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ResiduePairAtomDistances extends ResiduePairCaDistance {
    private final List<AtomPairDistance> atomDistances;
    private final double minAtomDistance;
    private final Atom closestAtom1, closestAtom2;

    private ResiduePairAtomDistances(Residue residue1, Residue residue2,
                                     double caDistance,
                                     List<AtomPairDistance> atomDistances, double minAtomDistance,
                                     Atom closestAtom1, Atom closestAtom2) {
        super(residue1, residue2, caDistance);
        this.atomDistances = atomDistances;
        this.minAtomDistance = minAtomDistance;
        this.closestAtom1 = closestAtom1;
        this.closestAtom2 = closestAtom2;
    }

    public ResiduePairAtomDistances(ResiduePairCaDistance residuePairCaDistance) {
        this(residuePairCaDistance.getResidue1(), residuePairCaDistance.getResidue2(),
                residuePairCaDistance.getCaDistance());
    }

    ResiduePairAtomDistances(Residue residue1, Residue residue2,
                             boolean noCaDistanceCalc) {
        this(noCaDistanceCalc ?
                new ResiduePairCaDistance(residue1, residue2, Double.NaN) :
                new ResiduePairCaDistance(residue1, residue2));
    }

    ResiduePairAtomDistances(Residue residue1, Residue residue2,
                             double caDistance) {
        super(residue1, residue2, caDistance);
        this.atomDistances = new ArrayList<>();
        double minAtomDistance = Double.POSITIVE_INFINITY;
        Atom closestAtom1 = null, closestAtom2 = null;

        for (Atom atom1 : residue1) {
            if (atom1.getAtomType() == AtomType.ATOM) {
                for (Atom atom2 : residue2) {
                    if (atom2.getAtomType() == AtomType.ATOM) {
                        var dist = new AtomPairDistance(atom1, atom2);
                        var d = dist.getDistance();
                        if (d < minAtomDistance) {
                            minAtomDistance = d;
                            closestAtom1 = atom1;
                            closestAtom2 = atom2;
                        }
                        atomDistances.add(dist);
                    }
                }
            }
        }

        this.minAtomDistance = minAtomDistance;
        this.closestAtom1 = closestAtom1;
        this.closestAtom2 = closestAtom2;
    }

    public ResiduePairAtomDistances filter(double maxDist) {
        return maxDist < 0 ? this : new ResiduePairAtomDistances(
                residue1, residue2, caDistance,
                atomDistances.stream().filter(x -> x.getDistance() <= maxDist).collect(Collectors.toList()),
                minAtomDistance, closestAtom1, closestAtom2
        );
    }

    public List<AtomPairDistance> getAtomDistances() {
        return Collections.unmodifiableList(atomDistances);
    }

    @Override
    public double getDistance() {
        return minAtomDistance;
    }

    public double getMinAtomDistance() {
        return minAtomDistance;
    }

    public Atom getClosestAtom1() {
        return closestAtom1;
    }

    public Atom getClosestAtom2() {
        return closestAtom2;
    }
}