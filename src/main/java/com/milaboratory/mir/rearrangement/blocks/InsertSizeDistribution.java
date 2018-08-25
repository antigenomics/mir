package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.probability.IntegerDistribution;

import java.util.Random;

public class InsertSizeDistribution extends IntegerDistribution {
    public InsertSizeDistribution(DistributionMap<Integer> distributionMap, Random random) {
        super(distributionMap, random);
    }

    public InsertSizeDistribution(DistributionAccumulator<Integer> distributionAccumulator, Random random) {
        super(distributionAccumulator, random);
    }
}
