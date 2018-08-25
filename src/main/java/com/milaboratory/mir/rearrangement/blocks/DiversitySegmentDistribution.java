package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Random;

public class DiversitySegmentDistribution extends Distribution<DiversitySegment> {
    public DiversitySegmentDistribution(DistributionMap<DiversitySegment> distributionMap, Random random) {
        super(distributionMap, DiversitySegment.class, random);
    }

    public DiversitySegmentDistribution(DistributionAccumulator<DiversitySegment> distributionAccumulator, Random random) {
        super(distributionAccumulator, DiversitySegment.class, random);
    }
}
