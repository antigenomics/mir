package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.probability.IntegerDistribution;

import java.util.Map;

public class InsertSizeDistribution extends IntegerDistribution {
    public InsertSizeDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }

    public InsertSizeDistribution(DistributionAccumulator<Integer> distributionAccumulator) {
        super(distributionAccumulator);
    }

    public static InsertSizeDistribution fromMap(Map<Integer, Double> probabilities) {
        return new InsertSizeDistribution(new DistributionMap<>(probabilities));
    }
}
