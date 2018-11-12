package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeWeighter<T extends Clonotype, K extends ClonotypeKey> {
    BinnedClonotype<T, K> weight(BinnedClonotype<T, K> subject);
}
