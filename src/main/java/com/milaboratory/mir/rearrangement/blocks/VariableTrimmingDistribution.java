package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class VariableTrimmingDistribution
        extends ConditionalDistribution1<VariableSegment, Integer, TrimmingDistribution>
        implements ModelBlock<VariableTrimmingDistribution> {
    VariableTrimmingDistribution(ConditionalDistribution1<VariableSegment, Integer, TrimmingDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, TrimmingDistribution::new, fromAccumulator);
    }

    private VariableTrimmingDistribution(Map<VariableSegment, TrimmingDistribution> probabilityMap) {
        super(probabilityMap, TrimmingDistribution::new, true);
    }

    public static VariableTrimmingDistribution fromMap(Map<VariableSegment, Map<Integer, Double>> probabilities) {
        return new VariableTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public VariableTrimmingDistribution copy(boolean fromAccumulator) {
        return new VariableTrimmingDistribution(this, fromAccumulator);
    }
}
