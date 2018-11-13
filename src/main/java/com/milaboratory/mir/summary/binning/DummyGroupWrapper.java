package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.ClonotypeGroupWrapper;
import com.milaboratory.mir.summary.WrappedClonotype;

import java.util.Collection;
import java.util.Collections;

public final class DummyGroupWrapper<T extends Clonotype> implements ClonotypeGroupWrapper<T, DummyGroup> {
    @Override
    public Collection<WrappedClonotype<T, DummyGroup>> create(T clonotype) {
        return Collections.singletonList(new WrappedClonotype<>(DummyGroup.INSTANCE, 1.0, clonotype));
    }
}
