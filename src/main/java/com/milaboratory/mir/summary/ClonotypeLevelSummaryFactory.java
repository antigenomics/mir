package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

public interface ClonotypeLevelSummaryFactory<T extends Clonotype, S extends ClonotypeLevelSummary<T>> {
    S create();
}
