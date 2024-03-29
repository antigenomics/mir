package com.antigenomics.mir.summary;

import com.antigenomics.mir.clonotype.Clonotype;

import java.util.Collection;

@FunctionalInterface
public interface ClonotypeGroupWrapper<T extends Clonotype, G extends ClonotypeGroup> {
    Collection<WrappedClonotype<T, G>> create(T clonotype);
}
