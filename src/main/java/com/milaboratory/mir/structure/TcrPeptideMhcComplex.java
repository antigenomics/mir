package com.milaboratory.mir.structure;

import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.structure.pdb.Structure;

public final class TcrPeptideMhcComplex {
    private final AntigenReceptor antigenReceptor;
    private final PeptideMhcComplex peptideMhcComplex;
    private final Species species;
    private final Structure structure;

    public TcrPeptideMhcComplex(AntigenReceptor antigenReceptor, PeptideMhcComplex peptideMhcComplex,
                                Structure structure) {
        this.antigenReceptor = antigenReceptor;
        this.peptideMhcComplex = peptideMhcComplex;
        // todo: introduce species/gene into segments
        this.species = Species.combine(
                peptideMhcComplex.getMhcComplex().getFirstChain().getMhcAllele().getSpecies(),
                peptideMhcComplex.getMhcComplex().getSecondChain().getMhcAllele().getSpecies()
        );
        this.structure = structure;
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

    public Structure getStructure() {
        return structure;
    }
}
