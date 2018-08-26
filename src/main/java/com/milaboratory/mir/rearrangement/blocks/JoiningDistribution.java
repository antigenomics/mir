package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class JoiningDistribution extends Distribution<JoiningSegment> {
    public JoiningDistribution(DistributionMap<JoiningSegment> distributionMap) {
        super(distributionMap, JoiningSegment.class);
    }

    public JoiningDistribution(DistributionAccumulator<JoiningSegment> distributionAccumulator) {
        super(distributionAccumulator, JoiningSegment.class);
    }

    public static JoiningDistribution fromMap(Map<JoiningSegment, Double> probabilities) {
        return new JoiningDistribution(new DistributionMap<>(probabilities));
    }
}
