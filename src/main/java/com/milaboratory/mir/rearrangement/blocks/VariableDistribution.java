package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class VariableDistribution extends Distribution<VariableSegment> {
    public VariableDistribution(DistributionMap<VariableSegment> distributionMap) {
        super(distributionMap, VariableSegment.class);
    }

    public VariableDistribution(DistributionAccumulator<VariableSegment> distributionAccumulator) {
        super(distributionAccumulator, VariableSegment.class);
    }

    public static VariableDistribution fromMap(Map<VariableSegment, Double> probabilities) {
        return new VariableDistribution(new DistributionMap<>(probabilities));
    }
}
