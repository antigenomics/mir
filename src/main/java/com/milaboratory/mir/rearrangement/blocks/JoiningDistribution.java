package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class JoiningDistribution
        extends Distribution<JoiningSegment>
        implements ModelBlock<JoiningDistribution> {
    JoiningDistribution(Distribution<JoiningSegment> toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private JoiningDistribution(DistributionMap<JoiningSegment> distributionMap) {
        super(distributionMap);
    }

    public static JoiningDistribution fromMap(Map<JoiningSegment, Double> probabilities) {
        return new JoiningDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public JoiningDistribution copy(boolean fromAccumulator) {
        return new JoiningDistribution(this, fromAccumulator);
    }
}
