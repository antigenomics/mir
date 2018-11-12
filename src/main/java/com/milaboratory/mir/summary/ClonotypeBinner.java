package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.Collection;

@FunctionalInterface
public interface ClonotypeBinner<T extends Clonotype, K extends ClonotypeKey> {
    Collection<BinnedClonotype<T, K>> createBins(T clonotype);
}
