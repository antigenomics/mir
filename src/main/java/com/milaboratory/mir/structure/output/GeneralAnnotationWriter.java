package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.*;

import java.io.OutputStream;

public class GeneralAnnotationWriter extends TableWriter<TcrPeptideMhcComplex> {
    public static final String HEADER =
            "pdb.id\tcomplex.species\tchain.component\tchain.supertype\tchain.type\tchain.id\tallele.info\tseq.length";

    public GeneralAnnotationWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    public String convert(TcrPeptideMhcComplex complex) {
        // todo: consider using JSON annotations/etc
        String prefix = complex.getStructure().getId() + "\t" + complex.getSpecies();

        String res = "";

        var tcrComplex = complex.getAntigenReceptor();
        var prefix1 = prefix + "\t" + ComplexComponentType.TCR + "\t" + tcrComplex.getAntigenReceptorType();
        res += prefix1 + "\t" + writeChain(tcrComplex.getFirstChain()) + "\n";
        res += prefix1 + "\t" + writeChain(tcrComplex.getSecondChain()) + "\n";

        var peptide = complex.getPeptideMhcComplex().getPeptideChain();
        res += prefix + "\t" + ComplexComponentType.PEPTIDE + "\t" + ComplexComponentType.PEPTIDE + "\t" +
                writeChain(peptide) + "\n";

        var mhcComplex = complex.getPeptideMhcComplex().getMhcComplex();
        prefix1 = prefix + "\t" + ComplexComponentType.MHC + "\t" + mhcComplex.getMhcClassType() + "\t";
        res += prefix1 + writeChain(mhcComplex.getFirstChain()) + "\n";
        res += prefix1 + writeChain(mhcComplex.getSecondChain());

        return res;
    }

    private String writeChain(StructureChainWithMarkup chain) {
        return chain.getChainTypeStr() + "\t" +
                chain.getStructureChainId() + "\t" +
                chain.getAlleleInfoStr() + "\t" +
                chain.getStructureChain().getSequence().size();
    }
}
