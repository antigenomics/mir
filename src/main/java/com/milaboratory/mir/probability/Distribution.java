package com.milaboratory.mir.probability;

public class Distribution<T> {
    private final DistributionMap<T> distributionMap;
    private final DistributionSampler<T> distributionSampler;
    private final DistributionAccumulator<T> distributionAccumulator;
    private final boolean isDummy;

    public Distribution(Distribution<T> toCopy, boolean fromAccumulator) {
        if (toCopy == null || toCopy.isDummy) {
            this.distributionMap = DistributionMap.getDummy();
            this.distributionSampler = DistributionSampler.getDummy();
            this.distributionAccumulator = DistributionAccumulator.getDummy();
            this.isDummy = true;
        } else {
            if (fromAccumulator) {
                var distributionAccumulator = toCopy.distributionAccumulator;
                this.distributionMap = distributionAccumulator.getNormalizedDistributionMap();
                this.distributionSampler = new DistributionSampler<>(distributionMap);
                this.distributionAccumulator = distributionAccumulator.createCleanInstance();
            } else {
                this.distributionMap = toCopy.distributionMap;
                this.distributionSampler = toCopy.distributionSampler;
                this.distributionAccumulator = toCopy.distributionAccumulator.createCleanInstance();
            }
            this.isDummy = false;
        }
    }

    public Distribution(DistributionMap<T> distributionMap) {
        this.distributionMap = distributionMap;
        this.distributionSampler = new DistributionSampler<>(distributionMap);
        this.distributionAccumulator = new FixedDistributionAccumulator<>(distributionMap.values());
        this.isDummy = false;
    }

    public Distribution(DistributionAccumulator<T> distributionAccumulator) {
        if (distributionAccumulator.values().isEmpty()) {
            throw new IllegalArgumentException("No values in accumulator.");
        }
        this.distributionMap = distributionAccumulator.getNormalizedDistributionMap();
        this.distributionSampler = new DistributionSampler<>(distributionMap);
        this.distributionAccumulator = distributionAccumulator.createCleanInstance();
        this.isDummy = false;
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
