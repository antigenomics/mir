package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;
import java.util.Random;

public class DiversityTrimming5Distribution
        extends ConditionalDistribution1<DiversitySegment, Integer, TrimmingDistribution> {
    public DiversityTrimming5Distribution(Map<DiversitySegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public DiversityTrimming5Distribution(Map<DiversitySegment, Map<Integer, Double>> probabilities,
                                          Random random) {
        this(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> new TrimmingDistribution(e.getValue(), random)
        ));
    }
}
