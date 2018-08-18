package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.JunctionMarkup;
import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.AbsentDiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.MissingConstantSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VJClonotypeBuilder implements ClonotypeBuilder<VJRearrangementInfo, ReadlessClonotypeImpl> {
    public VJClonotypeBuilder() {
    }

    @Override
    public ReadlessClonotypeImpl build(VJRearrangementInfo rearrangementInfo) {
        VariableSegment variableSegment = rearrangementInfo.getVSegment();
        JoiningSegment joiningSegment = rearrangementInfo.getJSegment();

        NucleotideSequence vPart = variableSegment.getTrimmedCdr3Part(rearrangementInfo.getVTrimming()),
                jPart = joiningSegment.getTrimmedCdr3Part(rearrangementInfo.getJTrimming());
        NucleotideSequence cdr3 = vPart.concatenate(rearrangementInfo.getVJInsert().concatenate(jPart));
        JunctionMarkup junctionMarkup = new JunctionMarkup(vPart.size(),
                vPart.size() + rearrangementInfo.getVJInsert().size());

        return new ReadlessClonotypeImpl(cdr3,
                variableSegment,
                AbsentDiversitySegment.INSTANCE,
                joiningSegment,
                MissingConstantSegment.INSTANCE,
                rearrangementInfo.getSegmentTrimming(),
                junctionMarkup);
    }
}
