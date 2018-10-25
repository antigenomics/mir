package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.AntigenReceptorChain;
import com.milaboratory.mir.structure.MhcChain;
import com.milaboratory.mir.structure.PeptideChain;
import com.milaboratory.mir.structure.TcrPeptideMhcComplex;

import java.io.OutputStream;

public class GeneralAnnotationWriter extends TableWriter<TcrPeptideMhcComplex> {
    public static final String HEADER = "id\tspecies\tchain\torder\ttype\tallele";

    public GeneralAnnotationWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    public String convert(TcrPeptideMhcComplex complex) {
        String prefix = complex.getStructure().getId() + "\t" +
                complex.getSpecies() + "\t";

        String res = "";

        res += prefix + convert(complex.getPeptideMhcComplex().getPeptideChain()) + "\n";
        res += prefix + convert(complex.getPeptideMhcComplex().getMhcComplex().getFirstChain(), 0) + "\n";
        res += prefix + convert(complex.getPeptideMhcComplex().getMhcComplex().getSecondChain(), 1) + "\n";
        res += prefix + convert(complex.getAntigenReceptor().getFirstChain(), 0) + "\n";
        res += prefix + convert(complex.getAntigenReceptor().getSecondChain(), 1);

        return res;
    }

    private String convert(PeptideChain peptideChain) {
        return peptideChain.getStructureChain().getChainIdentifier() + "\t0\tP\tNA";
    }

    private String convert(MhcChain mhcChain, int order) {
        return mhcChain.getStructureChain().getChainIdentifier() + "\t" + order + "\t" +
                "MHC\t" + mhcChain.getMhcAllele().getId();
    }

    private String convert(AntigenReceptorChain arChain, int order) {
        return arChain.getStructureChain().getChainIdentifier() + "\t" + order + "\t" +
                "TCR\t" + arChain.getVariableSegment().getId() + ":" + arChain.getJoiningSegment().getId();
    }
}
