package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.JunctionMarkup;
import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VDJClonotypeBuilder implements ClonotypeBuilder<VDJRearrangementInfo, ReadlessClonotypeImpl> {
    public VDJClonotypeBuilder() {
    }

    @Override
    public ReadlessClonotypeImpl build(VDJRearrangementInfo rearrangementInfo) {
        VariableSegment variableSegment = rearrangementInfo.getVSegment();
        DiversitySegment diversitySegment = rearrangementInfo.getDSegment();
        JoiningSegment joiningSegment = rearrangementInfo.getJSegment();

        NucleotideSequence vPart = variableSegment.getTrimmedCdr3Part(rearrangementInfo.getVTrimming()),
                dPart = diversitySegment.getTrimmedCdr3Part(rearrangementInfo.getDTrimming5(),
                        rearrangementInfo.getDTrimming3()),
                jPart = joiningSegment.getTrimmedCdr3Part(rearrangementInfo.getJTrimming());

        NucleotideSequence cdr3 = vPart;
        int vEnd = cdr3.size();
        cdr3 = cdr3.concatenate(rearrangementInfo.getVDInsert());
        int dStart = cdr3.size();
        cdr3 = cdr3.concatenate(dPart);
        int dEnd = cdr3.size();
        cdr3 = cdr3.concatenate(rearrangementInfo.getDJInsert());
        int jStart = cdr3.size();
        cdr3 = cdr3.concatenate(jPart);

        return new ReadlessClonotypeImpl(cdr3,
                rearrangementInfo.getVSegment(),
                rearrangementInfo.getDSegment(),
                rearrangementInfo.getJSegment(),
                rearrangementInfo.getSegmentTrimming(),
                new JunctionMarkup(vEnd, jStart, dStart, dEnd));
    }
}
