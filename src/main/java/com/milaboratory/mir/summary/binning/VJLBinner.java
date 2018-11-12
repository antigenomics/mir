package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public final class VJLBinner<T extends Clonotype> extends AbstractVJBinner<T, VJLKey> {
    @Override
    protected VJLKey createKey(VariableSegment variableSegment, JoiningSegment joiningSegment, Clonotype clonotype) {
        return new VJLKey(variableSegment, joiningSegment, clonotype.getCdr3Aa().size());
    }
}
