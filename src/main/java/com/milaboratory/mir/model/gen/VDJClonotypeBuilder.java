package com.milaboratory.mir.model.gen;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.JunctionMarkup;
import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.SegmentSequenceLibrary;

public class VDJClonotypeBuilder implements ClonotypeBuilder<VDJRearrangementInfo, ReadlessClonotypeImpl> {
    private final SegmentSequenceLibrary segmentSequenceLibrary;

    public VDJClonotypeBuilder(SegmentSequenceLibrary segmentSequenceLibrary) {
        this.segmentSequenceLibrary = segmentSequenceLibrary;
    }

    @Override
    public ReadlessClonotypeImpl build(VDJRearrangementInfo rearrangementInfo) {
        NucleotideSequence vPart = segmentSequenceLibrary.getTrimmedVCdr3Part(rearrangementInfo.getVSegment(),
                rearrangementInfo.getVTrimming()),
                dPart = segmentSequenceLibrary.getTrimmedDCdr3Part(rearrangementInfo.getDSegment(),
                        rearrangementInfo.getDTrimming5(), rearrangementInfo.getDTrimming3()),
                jPart = segmentSequenceLibrary.getTrimmedJCdr3Part(rearrangementInfo.getJSegment(),
                        rearrangementInfo.getJTrimming());

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
