package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.*;

import java.util.Collections;
import java.util.List;

public class ReadlessClonotypeImpl implements ClonotypeWithRearrangementInfo {
    private final SegmentTrimming segmentTrimming;
    private final JunctionMarkup junctionMarkup;
    private final NucleotideSequence cdr3nt;
    private final AminoAcidSequence cdr3aa;
    private final List<SegmentCall<VariableSegment>> variableSegmentCalls;
    private final List<SegmentCall<DiversitySegment>> diversitySegmentCalls;
    private final List<SegmentCall<JoiningSegment>> joiningSegmentCalls;
    private final List<SegmentCall<ConstantSegment>> constantSegmentCalls;

    public ReadlessClonotypeImpl(NucleotideSequence cdr3nt,
                                 VariableSegment variableSegment,
                                 DiversitySegment diversitySegment,
                                 JoiningSegment joiningSegment,
                                 ConstantSegment constantSegment,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup) {
        this(cdr3nt,
                SegmentCall.asCallList(variableSegment),
                SegmentCall.asCallList(diversitySegment),
                SegmentCall.asCallList(joiningSegment),
                SegmentCall.asCallList(constantSegment),
                segmentTrimming, junctionMarkup, AminoAcidSequence.translateFromCenter(cdr3nt));
    }

    public ReadlessClonotypeImpl(NucleotideSequence cdr3nt,
                                 List<SegmentCall<VariableSegment>> variableSegmentCalls,
                                 List<SegmentCall<DiversitySegment>> diversitySegmentCalls,
                                 List<SegmentCall<JoiningSegment>> joiningSegmentCalls,
                                 List<SegmentCall<ConstantSegment>> constantSegmentCalls,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup) {
        this(cdr3nt,
                variableSegmentCalls, diversitySegmentCalls, joiningSegmentCalls, constantSegmentCalls,
                segmentTrimming, junctionMarkup, AminoAcidSequence.translateFromCenter(cdr3nt));
    }

    public ReadlessClonotypeImpl(NucleotideSequence cdr3nt,
                                 List<SegmentCall<VariableSegment>> variableSegmentCalls,
                                 List<SegmentCall<DiversitySegment>> diversitySegmentCalls,
                                 List<SegmentCall<JoiningSegment>> joiningSegmentCalls,
                                 List<SegmentCall<ConstantSegment>> constantSegmentCalls,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup,
                                 AminoAcidSequence cdr3aa) {
        this.segmentTrimming = segmentTrimming;
        this.junctionMarkup = junctionMarkup;
        this.cdr3nt = cdr3nt;
        this.cdr3aa = cdr3aa;
        this.variableSegmentCalls = Collections.unmodifiableList(variableSegmentCalls);
        this.diversitySegmentCalls = Collections.unmodifiableList(diversitySegmentCalls);
        this.joiningSegmentCalls = Collections.unmodifiableList(joiningSegmentCalls);
        this.constantSegmentCalls = Collections.unmodifiableList(constantSegmentCalls);
    }

    @Override
    public SegmentTrimming getSegmentTrimming() {
        return segmentTrimming;
    }

    @Override
    public JunctionMarkup getJunctionMarkup() {
        return junctionMarkup;
    }

    @Override
    public NucleotideSequence getCdr3Nt() {
        return cdr3nt;
    }

    @Override
    public AminoAcidSequence getCdr3Aa() {
        return cdr3aa;
    }

    @Override
    public List<SegmentCall<VariableSegment>> getVariableSegmentCalls() {
        return variableSegmentCalls;
    }

    @Override
    public List<SegmentCall<DiversitySegment>> getDiversitySegmentCalls() {
        return diversitySegmentCalls;
    }

    @Override
    public List<SegmentCall<JoiningSegment>> getJoiningSegmentCalls() {
        return joiningSegmentCalls;
    }

    @Override
    public List<SegmentCall<ConstantSegment>> getConstantSegmentCalls() {
        return constantSegmentCalls;
    }
}
