package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.summary.ClonotypeGroupWrapper;
import com.antigenomics.mir.summary.WrappedClonotype;

import java.util.Collection;
import java.util.Collections;

public final class DummyGroupWrapper<T extends Clonotype> implements ClonotypeGroupWrapper<T, DummyGroup> {
    @Override
    public Collection<WrappedClonotype<T, DummyGroup>> create(T clonotype) {
        return Collections.singletonList(new WrappedClonotype<>(DummyGroup.INSTANCE, 1.0, clonotype));
    }
}
