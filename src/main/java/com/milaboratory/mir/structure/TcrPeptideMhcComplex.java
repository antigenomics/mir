package com.milaboratory.mir.structure;

import com.milaboratory.mir.segment.Species;

public class TcrPeptideMhcComplex {
    private final AntigenReceptor antigenReceptor;
    private final PeptideMhcComplex peptideMhcComplex;
    private final Species species;

    public TcrPeptideMhcComplex(AntigenReceptor antigenReceptor, PeptideMhcComplex peptideMhcComplex) {
        this.antigenReceptor = antigenReceptor;
        this.peptideMhcComplex = peptideMhcComplex;
        // todo: introduce species/gene into segments
        this.species = Species.combine(
                peptideMhcComplex.getMhcComplex().getFirstChain().getMhcAllele().getSpecies(),
                peptideMhcComplex.getMhcComplex().getSecondChain().getMhcAllele().getSpecies()
        );
    }

    public AntigenReceptor getAntigenReceptor() {
        return antigenReceptor;
    }

    public PeptideMhcComplex getPeptideMhcComplex() {
        return peptideMhcComplex;
    }

    public Species getSpecies() {
        return species;
    }
}
