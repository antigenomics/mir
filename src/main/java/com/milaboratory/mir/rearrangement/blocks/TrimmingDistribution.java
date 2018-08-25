package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.probability.IntegerDistribution;

import java.util.Random;

public class TrimmingDistribution extends IntegerDistribution {
    public TrimmingDistribution(DistributionMap<Integer> distributionMap, Random random) {
        super(distributionMap, random);
    }

    public TrimmingDistribution(DistributionAccumulator<Integer> distributionAccumulator, Random random) {
        super(distributionAccumulator, random);
    }
}
