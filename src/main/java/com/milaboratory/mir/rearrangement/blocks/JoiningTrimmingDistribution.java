package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class JoiningTrimmingDistribution
        extends ConditionalDistribution1<JoiningSegment, Integer, TrimmingDistribution> {

    public JoiningTrimmingDistribution(Map<JoiningSegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static JoiningTrimmingDistribution fromMap(Map<JoiningSegment, Map<Integer, Double>> probabilities) {
        return new JoiningTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }
}
