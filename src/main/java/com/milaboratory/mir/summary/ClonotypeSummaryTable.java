package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClonotypeSummaryTable<T extends Clonotype, G extends ClonotypeGroup, E extends GroupSummaryEntry<G>,
        C extends ClonotypeGroupSummary<T, G, E>> implements Consumer<T> {
    private final ClonotypeGroupWrapper<T, G> clonotypeGroupWrapper;
    private final ClonotypeGroupSummaryFactory<T, G, E, C> clonotypeGroupSummaryFactory;
    private final ConcurrentHashMap<G, C> countersByClonotypeKey = new ConcurrentHashMap<>();
    private final ClonotypeWeighter<T, G> clonotypeWeighter;

    public ClonotypeSummaryTable(ClonotypeGroupWrapper<T, G> clonotypeGroupWrapper,
                                 ClonotypeGroupSummaryFactory<T, G, E, C> clonotypeGroupSummaryFactory) {
        this(clonotypeGroupWrapper, clonotypeGroupSummaryFactory, subject -> subject);
    }

    public ClonotypeSummaryTable(ClonotypeGroupWrapper<T, G> clonotypeGroupWrapper,
                                 ClonotypeGroupSummaryFactory<T, G, E, C> clonotypeGroupSummaryFactory,
                                 ClonotypeWeighter<T, G> clonotypeWeighter) {
        this.clonotypeGroupWrapper = clonotypeGroupWrapper;
        this.clonotypeGroupSummaryFactory = clonotypeGroupSummaryFactory;
        this.clonotypeWeighter = clonotypeWeighter;
    }

    @Override
    public void accept(T clonotype) {
        clonotypeGroupWrapper
                .create(clonotype)
                .forEach(binnedClonotype ->
                        countersByClonotypeKey.computeIfAbsent(
                                binnedClonotype.getGroup(),
                                clonotypeGroupSummaryFactory::create
                        ).accept(clonotypeWeighter.weight(binnedClonotype)));
    }

    public ClonotypeGroupWrapper<T, G> getClonotypeGroupWrapper() {
        return clonotypeGroupWrapper;
    }

    public ClonotypeGroupSummaryFactory<T, G, E, C> getClonotypeGroupSummaryFactory() {
        return clonotypeGroupSummaryFactory;
    }

    public ClonotypeWeighter<T, G> getClonotypeWeighter() {
        return clonotypeWeighter;
    }

    public List<E> getCounters() {
        return countersByClonotypeKey.values()
                .stream()
                .flatMap(x -> x.getEntries().stream())
                .collect(Collectors.toUnmodifiableList());
    }
}
