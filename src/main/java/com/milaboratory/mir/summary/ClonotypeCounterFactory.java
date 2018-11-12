package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeCounterFactory<T extends Clonotype,
        K extends ClonotypeKey,
        C extends ClonotypeCounter<T, K>> {
    C create(K clonotypeKey);
}
