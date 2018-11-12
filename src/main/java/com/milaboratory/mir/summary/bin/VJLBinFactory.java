package com.milaboratory.mir.summary.bin;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VJLBinFactory extends VJBinFactory {
    @Override
    protected ClonotypeKey createKey(VariableSegment variableSegment, JoiningSegment joiningSegment, Clonotype clonotype) {
        return new VJLKey(variableSegment, joiningSegment, clonotype.getCdr3Aa().size());
    }
}
