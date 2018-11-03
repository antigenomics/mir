package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;

import java.util.List;

public final class ResidueBackbone {
    private final Residue residue;
    private final Atom N, CA, C;

    public ResidueBackbone(Residue residue) {
        this.residue = residue;
        Atom N = null, CA = null, C = null;

        for (Atom atom : residue.getAtoms()) {
            if (atom.getAtomName().equals(PdbParserUtils.N_ATOM_NAME)) {
                N = atom;
            } else if (atom.getAtomName().equals(PdbParserUtils.CA_ATOM_NAME)) {
                CA = atom;
            } else if (atom.getAtomName().equals(PdbParserUtils.C_ATOM_NAME)) {
                C = atom;
            }
        }

        this.N = N;
        this.CA = CA;
        this.C = C;
    }

    public Residue getResidue() {
        return residue;
    }

    public Atom getN() {
        return N;
    }

    public Atom getCA() {
        return CA;
    }

    public Atom getC() {
        return C;
    }

    @Override
    public String toString() {
        return residue.toShortString() + "\n" +
                N + "\n" +
                CA + "\n" +
                C;
    }
}
