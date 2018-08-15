package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.JunctionMarkup;
import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.SegmentSequenceLibrary;

public class VJClonotypeBuilder implements ClonotypeBuilder<VJRearrangementInfo, ReadlessClonotypeImpl> {
    private final SegmentSequenceLibrary segmentSequenceLibrary;

    public VJClonotypeBuilder(SegmentSequenceLibrary segmentSequenceLibrary) {
        this.segmentSequenceLibrary = segmentSequenceLibrary;
    }

    @Override
    public ReadlessClonotypeImpl build(VJRearrangementInfo rearrangementInfo) {
        NucleotideSequence vPart = segmentSequenceLibrary.getTrimmedVCdr3Part(rearrangementInfo.getVSegment(),
                rearrangementInfo.getVTrimming()),
                jPart = segmentSequenceLibrary.getTrimmedJCdr3Part(rearrangementInfo.getJSegment(),
                        rearrangementInfo.getJTrimming());
        NucleotideSequence cdr3 = vPart.concatenate(rearrangementInfo.getVJInsert().concatenate(jPart));
        JunctionMarkup junctionMarkup = new JunctionMarkup(vPart.size(),
                vPart.size() + rearrangementInfo.getVJInsert().size());
        return new ReadlessClonotypeImpl(cdr3, rearrangementInfo.getVSegment(), null,
                rearrangementInfo.getJSegment(), rearrangementInfo.getSegmentTrimming(), junctionMarkup);
    }
}
