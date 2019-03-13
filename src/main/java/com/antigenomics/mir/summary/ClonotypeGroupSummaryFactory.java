package com.antigenomics.mir.summary;

import com.antigenomics.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeGroupSummaryFactory<T extends Clonotype,
        G extends ClonotypeGroup,
        E extends GroupSummaryEntry<G>,
        C extends ClonotypeGroupSummary<T, G, E>> {
    C create(G clonotypeGroup);
}
