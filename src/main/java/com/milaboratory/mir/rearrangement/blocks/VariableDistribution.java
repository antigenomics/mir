package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.VariableSegment;

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
