package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public final class VJBinner<T extends Clonotype> extends AbstractVJBinner<T, VJKey> {
    @Override
    protected VJKey createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                              T clonotype) {
        return new VJKey(variableSegment,
                joiningSegment);
    }
}
