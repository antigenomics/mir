package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.probability.DistributionFactory;

import java.util.Map;

public class TwoSideTrimmingDistribution
        extends ConditionalDistribution1<Integer, Integer, TrimmingDistribution>
        implements ModelBlock<TwoSideTrimmingDistribution> {
    TwoSideTrimmingDistribution(ConditionalDistribution1<Integer, Integer, TrimmingDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, TrimmingDistribution::new, fromAccumulator);
    }

    public TwoSideTrimmingDistribution(Map<Integer, TrimmingDistribution> probabilityMap) {
        super(probabilityMap, TrimmingDistribution::new, true);
    }

    public static TwoSideTrimmingDistribution fromMap(Map<Integer, Map<Integer, Double>> probabilities) {
        return new TwoSideTrimmingDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public TwoSideTrimmingDistribution copy(boolean fromAccumulator) {
        return new TwoSideTrimmingDistribution(this, fromAccumulator);
    }
}
