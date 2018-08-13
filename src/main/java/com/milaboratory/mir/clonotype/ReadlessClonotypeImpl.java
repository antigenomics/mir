package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.SegmentId;

public class ReadlessClonotypeImpl implements ClonotypeWithRearrangementInfo {
    private final SegmentTrimming segmentTrimming;
    private final JunctionMarkup junctionMarkup;
    private final NucleotideSequence cdr3nt;
    private final AminoAcidSequence cdr3aa;
    private final SegmentId v, d, j;

    public ReadlessClonotypeImpl(NucleotideSequence cdr3nt,
                                 SegmentId v, SegmentId d, SegmentId j,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup) {
        this(cdr3nt, v, d, j, segmentTrimming, junctionMarkup, AminoAcidSequence.translateFromCenter(cdr3nt));
    }

    public ReadlessClonotypeImpl(NucleotideSequence cdr3nt,
                                 SegmentId v, SegmentId d, SegmentId j,
                                 SegmentTrimming segmentTrimming, JunctionMarkup junctionMarkup,
                                 AminoAcidSequence cdr3aa) {
        this.segmentTrimming = segmentTrimming;
        this.junctionMarkup = junctionMarkup;
        this.cdr3nt = cdr3nt;
        this.cdr3aa = cdr3aa;
        this.v = v;
        this.d = d;
        this.j = j;
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
    public SegmentId getV() {
        return v;
    }

    @Override
    public SegmentId getD() {
        return d;
    }

    @Override
    public SegmentId getJ() {
        return j;
    }
}
