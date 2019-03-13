package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;

public final class VJLGroupWrapper<T extends Clonotype> extends AbstractVJGroupWrapper<T, VJLGroup> {
    @Override
    protected VJLGroup createKey(VariableSegment variableSegment, JoiningSegment joiningSegment, Clonotype clonotype) {
        return new VJLGroup(variableSegment, joiningSegment, clonotype.getCdr3Aa().size());
    }
}
