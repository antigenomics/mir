package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityTrimming3Distribution
        extends ConditionalDistribution2<DiversitySegment, Integer, Integer,
        TrimmingDistribution, TwoSideTrimmingDistribution> {
    public DiversityTrimming3Distribution(Map<DiversitySegment,
            TwoSideTrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static DiversityTrimming3Distribution fromMap(
            Map<DiversitySegment, Map<Integer, Map<Integer, Double>>> probabilities) {
        return new DiversityTrimming3Distribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TwoSideTrimmingDistribution.fromMap(e.getValue())
        ));
    }
}
