package com.milaboratory.mir.structure;

public class TcrPeptideMhcComplex {
    private final AntigenReceptor antigenReceptor;
    private final PeptideMhcComplex peptideMhcComplex;

    public TcrPeptideMhcComplex(AntigenReceptor antigenReceptor, PeptideMhcComplex peptideMhcComplex) {
        this.antigenReceptor = antigenReceptor;
        this.peptideMhcComplex = peptideMhcComplex;
    }

    public AntigenReceptor getAntigenReceptor() {
        return antigenReceptor;
    }

    public PeptideMhcComplex getPeptideMhcComplex() {
        return peptideMhcComplex;
    }
}
