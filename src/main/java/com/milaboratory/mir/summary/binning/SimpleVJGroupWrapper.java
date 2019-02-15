package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.ClonotypeGroupWrapper;
import com.milaboratory.mir.summary.WrappedClonotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
