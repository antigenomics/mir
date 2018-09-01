package com.milaboratory.mir.probability;

public class IntegerDistribution extends Distribution<Integer> {
    public IntegerDistribution(IntegerDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    public IntegerDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }
}
