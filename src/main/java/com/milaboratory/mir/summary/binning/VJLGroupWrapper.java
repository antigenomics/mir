package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public final class VJLGroupWrapper<T extends Clonotype> extends AbstractVJGroupWrapper<T, VJLGroup> {
    @Override
    protected VJLGroup createKey(VariableSegment variableSegment, JoiningSegment joiningSegment, Clonotype clonotype) {
        return new VJLGroup(variableSegment, joiningSegment, clonotype.getCdr3Aa().size());
    }
}
