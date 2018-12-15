package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.structure.pdb.contacts.ChainPairwiseDistances;
import com.milaboratory.mir.structure.pdb.contacts.ResiduePairAtomDistances;
import com.milaboratory.mir.structure.pdb.contacts.ResiduePairDistance;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class AtomDistanceWriter extends CaDistanceWriter {
    private static final String HEADER =
            "pdb.id\tchain.id.from\tchain.id.to\tresidue.index.from\tresidue.index.to\tatom.from\tatom.to\tdist";

    public AtomDistanceWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    protected List<? extends ResiduePairDistance> getDistanceList(ChainPairwiseDistances chainPairwiseDistances) {
        return chainPairwiseDistances.getResiduePairAtomDistances();
    }

    @Override
    protected String writeResiduePairDistances(String prefix, ResiduePairDistance residuePairAtomDistances) {
        var atomDistances = ((ResiduePairAtomDistances) residuePairAtomDistances).getAtomDistances();
        return atomDistances.isEmpty() ? "" :
                atomDistances
                        .stream()
                        .map(x -> prefix + "\t" +
                                x.getAtom1().getAtomName().getLetter() + "\t" +
                                x.getAtom2().getAtomName().getLetter() + "\t" +
                                (float) x.getDistance())
                        .collect(Collectors.joining("\n"));
    }
}
