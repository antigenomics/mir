package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeWeighter<T extends Clonotype, G extends ClonotypeGroup> {
    WrappedClonotype<T, G> weight(WrappedClonotype<T, G> subject);
}
