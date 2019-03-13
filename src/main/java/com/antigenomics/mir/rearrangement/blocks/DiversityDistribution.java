package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.probability.Distribution;
import com.antigenomics.mir.probability.DistributionMap;
import com.antigenomics.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityDistribution
        extends Distribution<DiversitySegment>
        implements ModelBlock<DiversityDistribution> {
    DiversityDistribution(Distribution<DiversitySegment> toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private DiversityDistribution(DistributionMap<DiversitySegment> distributionMap) {
        super(distributionMap);
    }

    public static DiversityDistribution fromMap(Map<DiversitySegment, Double> probabilities) {
        return new DiversityDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public DiversityDistribution copy(boolean fromAccumulator) {
        return new DiversityDistribution(this, fromAccumulator);
    }
}
