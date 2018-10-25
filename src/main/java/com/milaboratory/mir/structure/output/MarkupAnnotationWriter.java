package com.milaboratory.mir.structure.output;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.*;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class MarkupAnnotationWriter extends TableWriter<TcrPeptideMhcComplex> {
    public static final String HEADER = "pdb.id\tchain.id\tregion.type\tregion.start\tregion.end\tregion.sequence";

    public MarkupAnnotationWriter(OutputStream os) {
        super(os, HEADER);
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

    private <E extends Enum<E>> String convert(String prefix,
                                               StructureChainWithMarkup<E> structureChainWithMarkup) {
        return convert(prefix + "\t" + structureChainWithMarkup.getStructureChainId(),
                structureChainWithMarkup.getMarkup());
    }

    private <E extends Enum<E>> String convert(
            String prefix,
            SequenceRegionMarkup<AminoAcidSequence, E, ? extends SequenceRegionMarkup> markup) {
        return markup.getAllRegions().values().stream()
                .map(x ->
                        prefix + "\t" +
                                x.getRegionType() + "\t" +
                                x.getStart() + "\t" +
                                x.getEnd() + "\t" +
                                x.getSequence()
                ).collect(Collectors.joining("\n"));
    }
}