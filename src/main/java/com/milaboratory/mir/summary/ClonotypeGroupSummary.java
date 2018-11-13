package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.Collection;
import java.util.function.Consumer;

public interface ClonotypeGroupSummary<T extends Clonotype,
        G extends ClonotypeGroup,
        E extends GroupSummaryEntry<G>>
        extends Consumer<WrappedClonotype<T, G>> {
    G getClonotypeGroup();

    Collection<E> getEntries();
}
