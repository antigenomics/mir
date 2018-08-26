package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;

import java.util.Map;

public class TwoSideTrimmingDistribution
        extends ConditionalDistribution1<Integer, Integer, TrimmingDistribution> {
    public TwoSideTrimmingDistribution(Map<Integer, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static TwoSideTrimmingDistribution fromMap(Map<Integer, Map<Integer, Double>> probabilities) {
        return new TwoSideTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }
}
