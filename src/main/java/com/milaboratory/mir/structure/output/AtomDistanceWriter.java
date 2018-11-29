package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.structure.pdb.contacts.ResiduePairDistances;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class AtomDistanceWriter extends CaDistanceWriter {
    private static final String HEADER =
            "pdb.id\tchain.id.from\tchain.id.to\tresidue.index.from\tresidue.index.to\tatom.from\tatom.to\tdist";

    public AtomDistanceWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    protected String writeResiduePairDistances(String prefix, ResiduePairDistances residuePairDistances) {
        return residuePairDistances
                .getAtomDistances()
                .stream()
                .map(x -> prefix + "\t" +
                        x.getAtom1().getAtomName().getLetter() + "\t" +
                        x.getAtom2().getAtomName().getLetter() + "\t" +
                        (float) x.getDistance())
                .collect(Collectors.joining("\n"));
    }
}
