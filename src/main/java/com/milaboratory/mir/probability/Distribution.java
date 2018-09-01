package com.milaboratory.mir.probability;

public class Distribution<T> {
    private final DistributionMap<T> distributionMap;
    private final DistributionSampler<T> distributionSampler;
    private final DistributionAccumulator<T> distributionAccumulator;

    public Distribution(Distribution<T> toCopy) {
        this.distributionMap = toCopy.distributionMap;
        this.distributionSampler = toCopy.distributionSampler;
        this.distributionAccumulator = toCopy.distributionAccumulator.createCleanInstance();
    }

    public Distribution(DistributionMap<T> distributionMap) {
        this.distributionMap = distributionMap;
        this.distributionSampler = new DistributionSampler<>(distributionMap);
        this.distributionAccumulator = new FixedDistributionAccumulator<>(distributionMap.values());
    }

    public Distribution(DistributionAccumulator<T> distributionAccumulator) {
        if (distributionAccumulator.values().isEmpty()) {
            throw new IllegalArgumentException("No values in accumulator.");
        }
        this.distributionMap = distributionAccumulator.getNormalizedDistributionMap();
        this.distributionSampler = new DistributionSampler<>(distributionMap);
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

    public Distribution<T> copy(boolean fromAccumulator) {
        return fromAccumulator ?
                new Distribution<>(distributionAccumulator) :
                new Distribution<>(this);
    }
}
