package com.antigenomics.mir.clonotype.rearrangement;

import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.segment.*;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.List;

public class ClonotypeWithReadImpl extends ReadlessClonotypeImpl {
    private final PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> markup;
    private final Mutations<NucleotideSequence> vMutations, dMutations, jMutations, cMutations;

    public ClonotypeWithReadImpl(PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> markup,
                                 Mutations<NucleotideSequence> vMutations,
                                 Mutations<NucleotideSequence> dMutations,
                                 Mutations<NucleotideSequence> jMutations,
                                 Mutations<NucleotideSequence> cMutations,
                                 List<SegmentCall<VariableSegment>> variableSegmentCalls,
                                 List<SegmentCall<DiversitySegment>> diversitySegmentCalls,
                                 List<SegmentCall<JoiningSegment>> joiningSegmentCalls,
                                 List<SegmentCall<ConstantSegment>> constantSegmentCalls,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup) {
        super(markup.getRegion(AntigenReceptorRegionType.CDR3).getSequence(),
                variableSegmentCalls, diversitySegmentCalls, joiningSegmentCalls, constantSegmentCalls,
                segmentTrimming, junctionMarkup);
        this.markup = markup;
        this.vMutations = vMutations;
        this.dMutations = dMutations;
        this.jMutations = jMutations;
        this.cMutations = cMutations;
    }

    public PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getMarkup() {
        return markup;
    }

    public Mutations<NucleotideSequence> getVMutations() {
        return vMutations;
    }

    public Mutations<NucleotideSequence> getDMutations() {
        return dMutations;
    }

    public Mutations<NucleotideSequence> getJMutations() {
        return jMutations;
    }

    public Mutations<NucleotideSequence> getCMutations() {
        return cMutations;
    }
}
