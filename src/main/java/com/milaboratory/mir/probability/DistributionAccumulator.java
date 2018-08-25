package com.milaboratory.mir.probability;

import com.milaboratory.mir.thirdparty.AtomicDouble;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DistributionAccumulator<T> {
    protected final Map<T, AtomicDouble> accumulatorMap;

    public DistributionAccumulator(Map<T, AtomicDouble> accumulatorMap) {
        this.accumulatorMap = accumulatorMap;
    }

    public abstract void update(T value, double weight);

    public abstract DistributionAccumulator<T> createCleanInstance();

    public DistributionMap<T> getNormalizedDistributionMap() {
        double total = accumulatorMap.values().stream().mapToDouble(AtomicDouble::get).sum();

        return new DistributionMap<>(
                accumulatorMap
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().get() / total
                        ))
        );
    }

    public Set<T> values() {
        return Collections.unmodifiableSet(accumulatorMap.keySet());
    }

    public void addPseudocount(double p) {
        accumulatorMap.values().forEach(x -> x.addAndGet(p));
    }

    public void addPseudocount() {
        addPseudocount(1d / values().size());
    }
}
