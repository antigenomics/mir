package com.milaboratory.mir.model.gen;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.SegmentTrimming;
import com.milaboratory.mir.segment.SegmentId;

public class VDJRearrangementInfo implements RearrangementInfo {
    private final SegmentId vSegment, jSegment, dSegment;
    private final int vTrimming, jTrimming, dTrimming5, dTrimming3, vdInsertSize, djInsertSize;
    private final NucleotideSequence vdInsert, djInsert;

    public VDJRearrangementInfo(SegmentId vSegment, SegmentId jSegment, SegmentId dSegment,
                                int vTrimming, int jTrimming, int dTrimming5, int dTrimming3,
                                int vdInsertSize, int djInsertSize,
                                NucleotideSequence vdInsert, NucleotideSequence djInsert) {
        this.vSegment = vSegment;
        this.jSegment = jSegment;
        this.dSegment = dSegment;
        this.vTrimming = vTrimming;
        this.jTrimming = jTrimming;
        this.dTrimming5 = dTrimming5;
        this.dTrimming3 = dTrimming3;
        this.vdInsertSize = vdInsertSize;
        this.djInsertSize = djInsertSize;
        this.vdInsert = vdInsert;
        this.djInsert = djInsert;
    }

    @Override
    public SegmentId getVSegment() {
        return vSegment;
    }

    @Override
    public SegmentId getJSegment() {
        return jSegment;
    }

    @Override
    public SegmentTrimming getSegmentTrimming() {
        return new SegmentTrimming(vTrimming, jTrimming, dTrimming5, dTrimming3);
    }

    public SegmentId getDSegment() {
        return dSegment;
    }

    public int getVTrimming() {
        return vTrimming;
    }

    public int getJTrimming() {
        return jTrimming;
    }

    public int getDTrimming5() {
        return dTrimming5;
    }

    public int getDTrimming3() {
        return dTrimming3;
    }

    public int getVDInsertSize() {
        return vdInsertSize;
    }

    public int getDJInsertSize() {
        return djInsertSize;
    }

    public NucleotideSequence getVDInsert() {
        return vdInsert;
    }

    public NucleotideSequence getDJInsert() {
        return djInsert;
    }
}
