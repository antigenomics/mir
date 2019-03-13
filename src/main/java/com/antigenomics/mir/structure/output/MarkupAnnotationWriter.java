package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.structure.StructureChainWithMarkup;
import com.antigenomics.mir.structure.TcrPeptideMhcComplex;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.TableWriter;
import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.antigenomics.mir.structure.*;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class MarkupAnnotationWriter extends TableWriter<TcrPeptideMhcComplex> {
    public static final String HEADER = "pdb.id\tchain.id\tregion.type\tregion.start\tregion.end\tregion.sequence";

    public MarkupAnnotationWriter(OutputStream os) {
        super(os, HEADER);
    }

    protected MarkupAnnotationWriter(OutputStream os, String header) {
        super(os, header);
    }

    @Override
    public String convert(TcrPeptideMhcComplex complex) {
        String prefix = complex.getStructure().getId();

        String res = "";

        res += convert(prefix, complex.getAntigenReceptor().getFirstChain()) + "\n";
        res += convert(prefix, complex.getAntigenReceptor().getSecondChain()) + "\n";
        res += convert(prefix, complex.getPeptideMhcComplex().getPeptideChain()) + "\n";
        res += convert(prefix, complex.getPeptideMhcComplex().getMhcComplex().getFirstChain()) + "\n";
        res += convert(prefix, complex.getPeptideMhcComplex().getMhcComplex().getSecondChain());

        return res;
    }

    private <E extends Enum<E>> String convert(
            String prefix,
            StructureChainWithMarkup<E> structureChainWithMarkup) {
        var prefix1 = prefix + "\t" + structureChainWithMarkup.getStructureChainId();
        return structureChainWithMarkup.getMarkup().getAllRegions().values().stream()
                .map(x -> convert(prefix1, x, structureChainWithMarkup))
                .filter(x -> !x.isEmpty()) // for downstream impl of seqres writer
                .collect(Collectors.joining("\n"));
    }

    protected <E extends Enum<E>> String convert(String prefix,
                                                 SequenceRegion<AminoAcidSequence, E> sequenceRegion,
                                                 StructureChainWithMarkup<E> structureChainWithMarkup) {
        return prefix + "\t" +
                sequenceRegion.getRegionType() + "\t" +
                sequenceRegion.getStart() + "\t" +
                sequenceRegion.getEnd() + "\t" +
                sequenceRegion.getSequence();
    }
}