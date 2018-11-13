package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public final class VJGroupWrapper<T extends Clonotype> extends AbstractVJGroupWrapper<T, VJGroup> {
    @Override
    protected VJGroup createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                T clonotype) {
        return new VJGroup(variableSegment,
                joiningSegment);
    }
}
