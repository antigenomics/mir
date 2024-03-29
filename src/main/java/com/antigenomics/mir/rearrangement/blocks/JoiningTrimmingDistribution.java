package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.probability.ConditionalDistribution1;
import com.antigenomics.mir.segment.JoiningSegment;

import java.util.Map;

public class JoiningTrimmingDistribution
        extends ConditionalDistribution1<JoiningSegment, Integer, TrimmingDistribution>
        implements ModelBlock<JoiningTrimmingDistribution> {
    JoiningTrimmingDistribution(ConditionalDistribution1<JoiningSegment, Integer, TrimmingDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, TrimmingDistribution::new, fromAccumulator);
    }

    private JoiningTrimmingDistribution(Map<JoiningSegment, TrimmingDistribution> probabilityMap) {
        super(probabilityMap, TrimmingDistribution::new, true);
    }

    public static JoiningTrimmingDistribution fromMap(Map<JoiningSegment, Map<Integer, Double>> probabilities) {
        return new JoiningTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public JoiningTrimmingDistribution copy(boolean fromAccumulator) {
        return new JoiningTrimmingDistribution(this, fromAccumulator);
    }
}
