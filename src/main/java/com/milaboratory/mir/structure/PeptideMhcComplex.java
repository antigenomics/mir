package com.milaboratory.mir.structure;

public final class PeptideMhcComplex {
    private final PeptideChain peptideChain;
    private final MhcComplex mhcComplex;

    public PeptideMhcComplex(PeptideChain peptideChain, MhcComplex mhcComplex) {
        this.peptideChain = peptideChain;
        this.mhcComplex = mhcComplex;
    }

    public PeptideChain getPeptideChain() {
        return peptideChain;
    }

    public MhcComplex getMhcComplex() {
        return mhcComplex;
    }
}
