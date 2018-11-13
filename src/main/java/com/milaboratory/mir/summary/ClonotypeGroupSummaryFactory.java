package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

@FunctionalInterface
public interface ClonotypeGroupSummaryFactory<T extends Clonotype,
        G extends ClonotypeGroup,
        E extends GroupSummaryEntry<G>,
        C extends ClonotypeGroupSummary<T, G, E>> {
    C create(G clonotypeGroup);
}
