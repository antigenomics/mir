package com.milaboratory.mir.probability;

import java.util.Random;

public class Distribution<T> {
    private final DistributionMap<T> distributionMap;
    private final DistributionSampler<T> distributionSampler;
    private final DistributionAccumulator<T> distributionAccumulator;

    public Distribution(DistributionMap<T> distributionMap,
                        Class<T> clazz) {
        this.distributionMap = distributionMap;
        this.distributionSampler = new DistributionSampler<>(distributionMap, clazz);
        this.distributionAccumulator = new FixedDistributionAccumulator<>(distributionMap.values());
    }

    public Distribution(DistributionAccumulator<T> distributionAccumulator,
                        Class<T> clazz) {
        if (distributionAccumulator.values().isEmpty()) {
            throw new IllegalArgumentException("No values in accumulator");
        }
        this.distributionMap = distributionAccumulator.getNormalizedDistributionMap();
        this.distributionSampler = new DistributionSampler<>(distributionMap, clazz);
        this.distributionAccumulator = distributionAccumulator.createCleanInstance();
    }

    public DistributionMap<T> getDistributionMap() {
        return distributionMap;
    }

    public DistributionSampler<T> getDistributionSampler() {
        return distributionSampler;
    }

    public DistributionAccumulator<T> getDistributionAccumulator() {
        return distributionAccumulator;
    }
}
