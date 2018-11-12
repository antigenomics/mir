package com.milaboratory.mir.summary.bin;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentCall;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.ArrayList;
import java.util.Collection;

public class VJBinFactory<T extends Clonotype> implements ClonotypeBinFactory<T> {
    @Override
    public Collection<ClonotypeBin> create(T clonotype) {
        float vWeightSum = 0, jWeightSum = 0;
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            vWeightSum += variableSegmentCall.getWeight();
        }
        for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
            jWeightSum += joiningSegmentSegmentCall.getWeight();
        }
        float weightSum = vWeightSum * jWeightSum;
        var res = new ArrayList<ClonotypeBin>();
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
                res.add(new ClonotypeBin(
                        createKey(variableSegmentCall.getSegment(),
                                joiningSegmentSegmentCall.getSegment(),
                                clonotype),
                        variableSegmentCall.getWeight() * joiningSegmentSegmentCall.getWeight() / weightSum
                ));
            }
        }
        return res;
    }

    protected ClonotypeKey createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                     T clonotype) {
        return new VJKey(variableSegment,
                joiningSegment);
    }
}
