package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.parser.AtomType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ResiduePairDistances {
    private final List<AtomPairDistance> atomDistances;
    private final Residue residue1, residue2;
    private final double caDistance;

    private ResiduePairDistances(List<AtomPairDistance> atomDistances,
                                 Residue residue1, Residue residue2, double caDistance) {
        this.atomDistances = atomDistances;
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.caDistance = caDistance;
    }

    public ResiduePairDistances(ResiduePairCaDistance residuePairCaDistance) {
        this(residuePairCaDistance.getResidue1(), residuePairCaDistance.getResidue2(),
                residuePairCaDistance.getCaDistance());
    }

    public ResiduePairDistances(Residue residue1, Residue residue2) {
        this(residue1, residue2, Double.NaN);
    }

    public ResiduePairDistances(Residue residue1, Residue residue2, double caDistance) {
        this.residue1 = residue1;
        this.residue2 = residue2;
        this.atomDistances = new ArrayList<>();
        this.caDistance = caDistance;

        for (Atom atom1 : residue1) {
            if (atom1.getAtomType() == AtomType.ATOM) {
                for (Atom atom2 : residue2) {
                    if (atom2.getAtomType() == AtomType.ATOM) {
                        atomDistances.add(new AtomPairDistance(atom1, atom2));
                    }
                }
            }
        }
    }

    public ResiduePairDistances filter(double maxDist) {
        return new ResiduePairDistances(
                atomDistances.stream().filter(x -> x.getDistance() <= maxDist).collect(Collectors.toList()),
                residue1, residue2, caDistance
        );
    }

    public List<AtomPairDistance> getAtomDistances() {
        return Collections.unmodifiableList(atomDistances);
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
