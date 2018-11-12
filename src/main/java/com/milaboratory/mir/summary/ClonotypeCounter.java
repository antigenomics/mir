package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.function.Consumer;

public interface ClonotypeCounter<T extends Clonotype, K extends ClonotypeKey> extends Consumer<BinnedClonotype<T, K>> {
    K getClonotypeKey();
}
