package com.antigenomics.mir.summary;

import com.antigenomics.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeWeighter<T extends Clonotype, G extends ClonotypeGroup> {
    WrappedClonotype<T, G> weight(WrappedClonotype<T, G> subject);
}
