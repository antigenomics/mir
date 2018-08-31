package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class VariableTrimmingDistribution
        extends ConditionalDistribution1<VariableSegment, Integer, TrimmingDistribution>
        implements Block<VariableTrimmingDistribution> {

    public VariableTrimmingDistribution(Map<VariableSegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static VariableTrimmingDistribution fromMap(Map<VariableSegment, Map<Integer, Double>> probabilities) {
        return new VariableTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }
}
