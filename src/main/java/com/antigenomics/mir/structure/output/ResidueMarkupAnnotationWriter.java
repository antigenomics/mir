package com.antigenomics.mir.structure.output;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.antigenomics.mir.structure.StructureChainWithMarkup;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class ResidueMarkupAnnotationWriter extends MarkupAnnotationWriter {
    public static final String HEADER = "pdb.id\tchain.id\tregion.type\tresidue.index\tresidue.index.pdb\tresidue.ins.code\tresidue.aa";

    public ResidueMarkupAnnotationWriter(OutputStream os) {
        super(os, HEADER);
    }

    protected <E extends Enum<E>> String convert(String prefix,
                                                 SequenceRegion<AminoAcidSequence, E> sequenceRegion,
                                                 StructureChainWithMarkup<E> structureChainWithMarkup) {
        if (sequenceRegion.isEmpty()) {
            return "";
        }
        var prefix1 = prefix + "\t" + sequenceRegion.getRegionType();
        var chainRegion = structureChainWithMarkup.getStructureChain().extractRegion(sequenceRegion);
        return chainRegion.getResidues().stream()
                .map(x ->
                        prefix1 + "\t" +
                                x.getSequentialResidueSequenceNumber() + "\t" +
                                x.getResidueSequenceNumber() + "\t" +
                                x.getResidueInsertionCode() + "\t" +
                                x.getResidueName().getLetter())
                .collect(Collectors.joining("\n"));
    }
}
