package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Random;

public class JoiningSegmentDistribution extends Distribution<JoiningSegment> {
    public JoiningSegmentDistribution(DistributionMap<JoiningSegment> distributionMap,
                                      Random random) {
        super(distributionMap, JoiningSegment.class, random);
    }

    public JoiningSegmentDistribution(DistributionAccumulator<JoiningSegment> distributionAccumulator,
                                      Random random) {
        super(distributionAccumulator, JoiningSegment.class, random);
    }
}
