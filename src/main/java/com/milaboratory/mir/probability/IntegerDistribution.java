package com.milaboratory.mir.probability;

public class IntegerDistribution extends Distribution<Integer> {
    public IntegerDistribution(IntegerDistribution toCopy) {
        super(toCopy);
    }

    public IntegerDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }

    public IntegerDistribution(DistributionAccumulator<Integer> distributionAccumulator) {
        super(distributionAccumulator);
    }
}
