package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;

import java.util.List;

public final class ResidueBackBone {
    private final Atom N, CA, C;

    public ResidueBackBone(Residue residue) {
        this(residue.getAtoms());
    }

    public ResidueBackBone(List<? extends Atom> atoms) {
        Atom N = null, CA = null, C = null;

        for (Atom atom : atoms) {
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

    public Atom getN() {
        return N;
    }

    public Atom getCA() {
        return CA;
    }

    public Atom getC() {
        return C;
    }
}
