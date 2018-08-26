package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityTrimming5Distribution
        extends ConditionalDistribution1<DiversitySegment, Integer, TrimmingDistribution> {
    public DiversityTrimming5Distribution(Map<DiversitySegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static DiversityTrimming5Distribution fromMap(Map<DiversitySegment, Map<Integer, Double>> probabilities) {
        return new DiversityTrimming5Distribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }
}
