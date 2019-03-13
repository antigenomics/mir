package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.probability.DistributionMap;
import com.antigenomics.mir.probability.IntegerDistribution;

import java.util.Map;

public class TrimmingDistribution
        extends IntegerDistribution
        implements ModelBlock<TrimmingDistribution> {
    TrimmingDistribution(TrimmingDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private TrimmingDistribution(DistributionMap<Integer> distributionMap) {
        super(distributionMap);
    }

    public static TrimmingDistribution fromMap(Map<Integer, Double> probabilities) {
        return new TrimmingDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public TrimmingDistribution copy(boolean fromAccumulator) {
        return new TrimmingDistribution(this, fromAccumulator);
    }
}
