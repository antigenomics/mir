package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.SegmentTrimming;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VJRearrangementInfo implements RearrangementInfo {
    private final VariableSegment vSegment;
    private final JoiningSegment jSegment;
    private final int vTrimming, jTrimming, vjInsertSize;
    private final NucleotideSequence vjInsert;

    public VJRearrangementInfo(VariableSegment vSegment, JoiningSegment jSegment,
                               int vTrimming, int jTrimming, int vjInsertSize,
                               NucleotideSequence vjInsert) {
        this.vSegment = vSegment;
        this.jSegment = jSegment;
        this.vTrimming = vTrimming;
        this.jTrimming = jTrimming;
        this.vjInsertSize = vjInsertSize;
        this.vjInsert = vjInsert;
    }

    @Override
    public VariableSegment getVSegment() {
        return vSegment;
    }

    @Override
    public JoiningSegment getJSegment() {
        return jSegment;
    }

    @Override
    public SegmentTrimming getSegmentTrimming() {
        return new SegmentTrimming(vTrimming, jTrimming);
    }

    public int getVTrimming() {
        return vTrimming;
    }

    public int getJTrimming() {
        return jTrimming;
    }

    public int getVJInsertSize() {
        return vjInsertSize;
    }

    public NucleotideSequence getVJInsert() {
        return vjInsert;
    }
}
