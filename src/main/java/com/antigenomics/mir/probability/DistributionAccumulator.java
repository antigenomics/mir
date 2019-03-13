package com.antigenomics.mir.probability;

import com.antigenomics.mir.thirdparty.AtomicDouble;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DistributionAccumulator<T> {
    protected final Map<T, AtomicDouble> accumulatorMap;

    public static <T> DistributionAccumulator<T> getDummy() {
        return new DistributionAccumulator<>() {
            @Override
            public void update(T value, double weight) {
                throw new UnsupportedOperationException("Cannot update dummy accumulator");
            }

            @Override
            public DistributionAccumulator<T> createCleanInstance() {
                throw new UnsupportedOperationException("Cannot reinstantize dummy accumulator");
            }

            @Override
            public boolean isDummy() {
                return true;
            }
        };
    }

    private DistributionAccumulator() {
        this.accumulatorMap = null;
    }

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

    public boolean isDummy() {
        return false;
    }
}
