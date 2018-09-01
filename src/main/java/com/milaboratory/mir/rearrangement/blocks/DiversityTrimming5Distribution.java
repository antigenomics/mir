package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.probability.DistributionFactory;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityTrimming5Distribution
        extends ConditionalDistribution1<DiversitySegment, Integer, TrimmingDistribution>
        implements ModelBlock<DiversityTrimming5Distribution> {
    DiversityTrimming5Distribution(ConditionalDistribution1<DiversitySegment, Integer, TrimmingDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, TrimmingDistribution::new, fromAccumulator);
    }

    private DiversityTrimming5Distribution(Map<DiversitySegment, TrimmingDistribution> probabilityMap) {
        super(probabilityMap, TrimmingDistribution::new, true);
    }

    public static DiversityTrimming5Distribution fromMap(Map<DiversitySegment, Map<Integer, Double>> probabilities) {
        return new DiversityTrimming5Distribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TrimmingDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public DiversityTrimming5Distribution copy(boolean fromAccumulator) {
        return new DiversityTrimming5Distribution(this, fromAccumulator);
    }
}
