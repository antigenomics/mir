package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.SegmentCall;
import com.antigenomics.mir.segment.VariableSegment;
import com.antigenomics.mir.summary.WrappedClonotype;
import com.antigenomics.mir.summary.ClonotypeGroupWrapper;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractVJGroupWrapper<T extends Clonotype, G extends VJGroup> implements ClonotypeGroupWrapper<T, G> {
    @Override
    public Collection<WrappedClonotype<T, G>> create(T clonotype) {
        float vWeightSum = 0, jWeightSum = 0;
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            vWeightSum += variableSegmentCall.getWeight();
        }
        for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
            jWeightSum += joiningSegmentSegmentCall.getWeight();
        }
        float weightSum = vWeightSum * jWeightSum;
        var res = new ArrayList<WrappedClonotype<T, G>>();
        for (SegmentCall<VariableSegment> variableSegmentCall : clonotype.getVariableSegmentCalls()) {
            for (SegmentCall<JoiningSegment> joiningSegmentSegmentCall : clonotype.getJoiningSegmentCalls()) {
                res.add(new WrappedClonotype<>(
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

    protected abstract G createKey(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                   T clonotype);
}
