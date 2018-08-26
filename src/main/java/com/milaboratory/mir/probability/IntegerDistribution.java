package com.milaboratory.mir.probability;

public class IntegerDistribution extends Distribution<Integer> {
    public IntegerDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap, Integer.class);
    }

    public IntegerDistribution(DistributionAccumulator<Integer> distributionAccumulator) {
        super(distributionAccumulator, Integer.class);
    }
}
