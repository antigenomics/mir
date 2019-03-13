package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.probability.DistributionMap;
import com.antigenomics.mir.probability.IntegerDistribution;

import java.util.Map;

public class InsertSizeDistribution
        extends IntegerDistribution
        implements ModelBlock<InsertSizeDistribution> {
    InsertSizeDistribution(InsertSizeDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private InsertSizeDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }

    public static InsertSizeDistribution fromMap(Map<Integer, Double> probabilities) {
        return new InsertSizeDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public InsertSizeDistribution copy(boolean fromAccumulator) {
        return new InsertSizeDistribution(this, fromAccumulator);
    }
}
