package com.milaboratory.mir.probability;

import java.util.Random;

public class IntegerDistribution extends Distribution<Integer> {
    public IntegerDistribution(DistributionMap<Integer> distributionMap, Random random) {
        super(distributionMap, Integer.class, random);
    }

    public IntegerDistribution(DistributionAccumulator<Integer> distributionAccumulator, Random random) {
        super(distributionAccumulator, Integer.class, random);
    }
}
