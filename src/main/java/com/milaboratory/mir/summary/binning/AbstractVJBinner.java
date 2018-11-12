package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentCall;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.summary.BinnedClonotype;
import com.milaboratory.mir.summary.ClonotypeBinner;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractVJBinner<T extends Clonotype, K extends VJKey> implements ClonotypeBinner<T, K> {
    @Override
    public Collection<BinnedClonotype<T, K>> createBins(T clonotype) {
        float vWeightSum = 0, jWeightSum = 0;
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            vWeightSum += variableSegmentCall.getWeight();
        }
        for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
            jWeightSum += joiningSegmentSegmentCall.getWeight();
        }
        float weightSum = vWeightSum * jWeightSum;
        var res = new ArrayList<BinnedClonotype<T, K>>();
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
                res.add(new BinnedClonotype<>(
                        createKey(variableSegmentCall.getSegment(),
                                joiningSegmentSegmentCall.getSegment(),
                                clonotype),
                        // todo: check nan
                        variableSegmentCall.getWeight() * joiningSegmentSegmentCall.getWeight() / weightSum,
                        clonotype
                ));
            }
        }
        return res;
    }

    protected abstract K createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                   T clonotype);
}
