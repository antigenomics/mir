package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.Distribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityDistribution extends Distribution<DiversitySegment> {
    public DiversityDistribution(DistributionMap<DiversitySegment> distributionMap) {
        super(distributionMap, DiversitySegment.class);
    }

    public DiversityDistribution(DistributionAccumulator<DiversitySegment> distributionAccumulator) {
        super(distributionAccumulator, DiversitySegment.class);
    }

    public static DiversityDistribution fromMap(Map<DiversitySegment, Double> probabilities) {
        return new DiversityDistribution(new DistributionMap<>(probabilities));
    }
}
