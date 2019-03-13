package com.antigenomics.mir.summary;

import com.antigenomics.mir.clonotype.Clonotype;

import java.util.Collection;
import java.util.function.Consumer;

public interface ClonotypeGroupSummary<T extends Clonotype,
        G extends ClonotypeGroup,
        E extends GroupSummaryEntry<G>>
        extends Consumer<WrappedClonotype<T, G>> {
    G getClonotypeGroup();

    Collection<E> getCounters();
}
