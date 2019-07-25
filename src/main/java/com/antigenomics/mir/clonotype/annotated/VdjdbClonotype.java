package com.antigenomics.mir.clonotype.annotated;

import com.antigenomics.mir.Species;
import com.antigenomics.mir.clonotype.rearrangement.JunctionMarkup;
import com.antigenomics.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.antigenomics.mir.clonotype.rearrangement.SegmentTrimming;
import com.antigenomics.mir.mhc.MhcAllele;
import com.antigenomics.mir.segment.*;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VdjdbClonotype extends ReadlessClonotypeImpl {
    private final Species species;
    private final Gene gene;
    private final MhcAllele mhcAlleleA, mhcAlleleB;
    private final AminoAcidSequence antigenEpitope;

    public VdjdbClonotype(List<SegmentCall<VariableSegment>> variableSegmentCalls,
                          List<SegmentCall<JoiningSegment>> joiningSegmentCalls,
                          AminoAcidSequence cdr3aa,
                          JunctionMarkup junctionMarkup,
                          Map<String, String> annotations,
                          NucleotideSequence cdr3nt,
                          AminoAcidSequence antigenEpitope,
                          Species species,
                          Gene gene,
                          MhcAllele mhcAlleleA, MhcAllele mhcAlleleB) {
        super(cdr3nt,
                variableSegmentCalls, Collections.emptyList(), joiningSegmentCalls, Collections.emptyList(),
                annotations, SegmentTrimming.DUMMY, junctionMarkup, cdr3aa);

        this.species = species;
        this.gene = gene;
        this.mhcAlleleA = mhcAlleleA;
        this.mhcAlleleB = mhcAlleleB;
        this.antigenEpitope = antigenEpitope;
    }

    public Species getSpecies() {
        return species;
    }

    public Gene getGene() {
        return gene;
    }

    public MhcAllele getMhcAlleleA() {
        return mhcAlleleA;
    }

    public MhcAllele getMhcAlleleB() {
        return mhcAlleleB;
    }

    public AminoAcidSequence getAntigenEpitope() {
        return antigenEpitope;
    }
}
