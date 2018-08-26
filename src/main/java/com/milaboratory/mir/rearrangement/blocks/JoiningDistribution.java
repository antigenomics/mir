package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Random;

public class JoiningDistribution extends Distribution<JoiningSegment> {
    public JoiningDistribution(DistributionMap<JoiningSegment> distributionMap,
                               Random random) {
        super(distributionMap, JoiningSegment.class, random);
    }

    public JoiningDistribution(DistributionAccumulator<JoiningSegment> distributionAccumulator,
                               Random random) {
        super(distributionAccumulator, JoiningSegment.class, random);
    }
}
