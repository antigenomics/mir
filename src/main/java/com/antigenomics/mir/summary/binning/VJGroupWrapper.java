package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;

public final class VJGroupWrapper<T extends Clonotype> extends AbstractVJGroupWrapper<T, VJGroup> {
    @Override
    protected VJGroup createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                T clonotype) {
        return new VJGroup(variableSegment,
                joiningSegment);
    }
}
