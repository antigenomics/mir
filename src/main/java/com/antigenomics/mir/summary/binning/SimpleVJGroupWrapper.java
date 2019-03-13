package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.summary.ClonotypeGroupWrapper;
import com.antigenomics.mir.summary.WrappedClonotype;

import java.util.ArrayList;
import java.util.Collection;

public final class SimpleVJGroupWrapper<T extends Clonotype>
        implements ClonotypeGroupWrapper<T, VJGroup> {
    @Override
    public Collection<WrappedClonotype<T, VJGroup>> create(T clonotype) {
        var res = new ArrayList<WrappedClonotype<T, VJGroup>>();
        res.add(new WrappedClonotype<>(
                new VJGroup(clonotype.getVariableSegmentCalls().get(0).getSegment(),
                        clonotype.getJoiningSegmentCalls().get(0).getSegment()),
                1.0, clonotype));
        return res;
      //  return Collections.singletonList(new WrappedClonotype<>(
        //        new VJGroup(clonotype.getBestVariableSegment(),
          //              clonotype.getBestJoiningSegment()),
            //    1.0, clonotype));
    }
}
