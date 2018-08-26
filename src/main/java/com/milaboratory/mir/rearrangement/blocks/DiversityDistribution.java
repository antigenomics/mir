package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;
import java.util.Random;

public class DiversityDistribution extends Distribution<DiversitySegment> {
    public DiversityDistribution(DistributionMap<DiversitySegment> distributionMap, Random random) {
        super(distributionMap, DiversitySegment.class, random);
    }

    public DiversityDistribution(DistributionAccumulator<DiversitySegment> distributionAccumulator, Random random) {
        super(distributionAccumulator, DiversitySegment.class, random);
    }

    public DiversityDistribution(Map<DiversitySegment, Double> probabilities, Random random) {
        this(new DistributionMap<>(probabilities), random);
    }
}
