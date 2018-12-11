package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.pdb.contacts.ChainPairwiseDistances;
import com.milaboratory.mir.structure.pdb.contacts.ResiduePairDistances;
import com.milaboratory.mir.structure.pdb.contacts.StructurePairwiseDistances;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class CaDistanceWriter extends TableWriter<StructurePairwiseDistances> {
    private static final String HEADER =
            "pdb.id\tchain.id.from\tchain.id.to\tresidue.index.from\tresidue.index.to\tdist.ca";

    public CaDistanceWriter(OutputStream os) {
        super(os, HEADER);
    }

    protected CaDistanceWriter(OutputStream os, String header) {
        super(os, header);
    }

    @Override
    protected String convert(StructurePairwiseDistances obj) {
        return obj
                .getChainPairDistances()
                .stream()
                .map(x -> writeChainPair(
                        obj.getStructure().getId() + "\t" +
                                x.getChain1().getChainIdentifier() + "\t" +
                                x.getChain2().getChainIdentifier(),
                        x))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private String writeChainPair(String prefix, ChainPairwiseDistances chainPairwiseDistances) {
        return chainPairwiseDistances
                .getResiduePairDistances()
                .stream()
                .map(x ->
                        writeResiduePairDistances(prefix + "\t" +
                                        x.getResidue1().getSequentialResidueSequenceNumber() + "\t" +
                                        x.getResidue2().getSequentialResidueSequenceNumber(),
                                x)
                )
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    protected String writeResiduePairDistances(String prefix, ResiduePairDistances residuePairDistances) {
        return prefix + "\t" + (float) residuePairDistances.getCaDistance();
    }
}
