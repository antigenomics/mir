package com.milaboratory.mir.rearrangement;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
import com.milaboratory.mir.segment.*;

import java.util.Arrays;
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
}
