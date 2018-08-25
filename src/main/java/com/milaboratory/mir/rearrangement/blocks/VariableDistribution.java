package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Random;

public class VariableDistribution extends Distribution<VariableSegment> {
    public VariableDistribution(DistributionMap<VariableSegment> distributionMap,
                                Random random) {
        super(distributionMap, VariableSegment.class, random);
    }

    public VariableDistribution(DistributionAccumulator<VariableSegment> distributionAccumulator,
                                Random random) {
        super(distributionAccumulator, VariableSegment.class, random);
    }
}
