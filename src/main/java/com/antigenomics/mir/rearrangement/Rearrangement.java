package com.antigenomics.mir.rearrangement;

import com.antigenomics.mir.segment.*;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.rearrangement.JunctionMarkup;
import com.antigenomics.mir.segment.*;

import java.util.Collections;
import java.util.List;

public class Rearrangement implements Clonotype {
    private final RearrangementTemplate rearrangementTemplate;
    private final JunctionMarkup junctionMarkup;
    private final NucleotideSequence cdr3;

    public Rearrangement(RearrangementTemplate rearrangementTemplate,
                         JunctionMarkup junctionMarkup, NucleotideSequence cdr3) {
        this.rearrangementTemplate = rearrangementTemplate;
        this.junctionMarkup = junctionMarkup;
        this.cdr3 = cdr3;
    }

    public RearrangementTemplate getRearrangementTemplate() {
        return rearrangementTemplate;
    }

    public JunctionMarkup getJunctionMarkup() {
        return junctionMarkup;
    }

    public NucleotideSequence getCdr3() {
        return cdr3;
    }

    @Override
    public NucleotideSequence getCdr3Nt() {
        return cdr3;
    }

    @Override
    public AminoAcidSequence getCdr3Aa() {
        return AminoAcidSequence.translateFromCenter(cdr3);
    }

    @Override
    public List<SegmentCall<VariableSegment>> getVariableSegmentCalls() {
        return Collections.singletonList(SegmentCall.asCall(rearrangementTemplate.getVariableSegment()));
    }

    @Override
    public List<SegmentCall<DiversitySegment>> getDiversitySegmentCalls() {
        return Collections.singletonList(SegmentCall.asCall(rearrangementTemplate.getDiversitySegment()));
    }

    @Override
    public List<SegmentCall<JoiningSegment>> getJoiningSegmentCalls() {
        return Collections.singletonList(SegmentCall.asCall(rearrangementTemplate.getJoiningSegment()));
    }

    @Override
    public List<SegmentCall<ConstantSegment>> getConstantSegmentCalls() {
        return Collections.singletonList(SegmentCall.asCall(rearrangementTemplate.getConstantSegment()));
    }

    @Override
    public String toString() {
        return getCdr3Nt() + "\t" + getCdr3Aa() + "\t" +
                rearrangementTemplate.getVariableSegment().getId() + "\t" +
                rearrangementTemplate.getDiversitySegment().getId() + "\t" +
                rearrangementTemplate.getJoiningSegment().getId() + "\t" +
                rearrangementTemplate.getConstantSegment().getId() + "\t" +
                junctionMarkup.toString().replaceAll("\t", ":") + "\t" +
                rearrangementTemplate.getSegmentTrimming().toString().replaceAll("\t", ":");
    }
}
