package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ClonotypeSummaryTable<T extends Clonotype, K extends ClonotypeKey,
        C extends ClonotypeCounter<T, K>> implements Consumer<T> {
    private final ClonotypeBinner<T, K> clonotypeBinner;
    private final ClonotypeCounterFactory<T, K, C> clonotypeCounterFactory;
    private final ConcurrentHashMap<K, C> countersByClonotypeKey = new ConcurrentHashMap<>();
    private final ClonotypeWeighter<T, K> clonotypeWeighter;

    public ClonotypeSummaryTable(ClonotypeBinner<T, K> clonotypeBinner,
                                 ClonotypeCounterFactory<T, K, C> clonotypeCounterFactory) {
        this(clonotypeBinner, clonotypeCounterFactory, subject -> subject);
    }

    public ClonotypeSummaryTable(ClonotypeBinner<T, K> clonotypeBinner,
                                 ClonotypeCounterFactory<T, K, C> clonotypeCounterFactory,
                                 ClonotypeWeighter<T, K> clonotypeWeighter) {
        this.clonotypeBinner = clonotypeBinner;
        this.clonotypeCounterFactory = clonotypeCounterFactory;
        this.clonotypeWeighter = clonotypeWeighter;
    }

    @Override
    public void accept(T clonotype) {
        clonotypeBinner
                .createBins(clonotype)
                .forEach(binnedClonotype ->
                        countersByClonotypeKey.computeIfAbsent(
                                binnedClonotype.getClonotypeKey(),
                                clonotypeCounterFactory::create
                        ).accept(clonotypeWeighter.weight(binnedClonotype)));
    }

    public ClonotypeBinner<T, K> getClonotypeBinner() {
        return clonotypeBinner;
    }

    public ClonotypeCounterFactory<T, K, C> getClonotypeCounterFactory() {
        return clonotypeCounterFactory;
    }

    public ClonotypeWeighter<T, K> getClonotypeWeighter() {
        return clonotypeWeighter;
    }

    public Collection<C> getCounters() {
        return Collections.unmodifiableCollection(countersByClonotypeKey.values());
    }
}
