package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.probability.IntegerDistribution;

import java.util.Map;
import java.util.Random;

public class TrimmingDistribution extends IntegerDistribution {
    public TrimmingDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }

    public TrimmingDistribution(DistributionAccumulator<Integer> distributionAccumulator) {
        super(distributionAccumulator);
    }

    public static TrimmingDistribution fromMap(Map<Integer, Double> probabilities) {
        return new TrimmingDistribution(new DistributionMap<>(probabilities));
    }
}
