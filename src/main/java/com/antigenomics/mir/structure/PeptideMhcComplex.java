package com.antigenomics.mir.structure;

import com.antigenomics.mir.structure.pdb.ChainRegion;

import java.util.ArrayList;
import java.util.List;

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

    public List<ChainRegion> getRegions() {
        var regions = new ArrayList<ChainRegion>();

        regions.addAll(getMhcComplex().getRegions());
        regions.addAll(getPeptideChain().getRegions());

        return regions;
    }
}
