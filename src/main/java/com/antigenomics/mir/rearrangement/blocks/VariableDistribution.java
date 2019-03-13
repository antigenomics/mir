package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.probability.Distribution;
import com.antigenomics.mir.probability.DistributionMap;
import com.antigenomics.mir.segment.VariableSegment;

import java.util.Map;

public class VariableDistribution
        extends Distribution<VariableSegment>
        implements ModelBlock<VariableDistribution> {
    VariableDistribution(VariableDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private VariableDistribution(DistributionMap<VariableSegment> distributionMap) {
        super(distributionMap);
    }

    public static VariableDistribution fromMap(Map<VariableSegment, Double> probabilities) {
        return new VariableDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public VariableDistribution copy(boolean fromAccumulator) {
        return new VariableDistribution(this, fromAccumulator);
    }
}
