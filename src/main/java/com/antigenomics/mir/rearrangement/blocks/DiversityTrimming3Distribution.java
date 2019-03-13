package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.probability.ConditionalDistribution2;
import com.antigenomics.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversityTrimming3Distribution
        extends ConditionalDistribution2<DiversitySegment, Integer, Integer,
        TrimmingDistribution, TwoSideTrimmingDistribution>
        implements ModelBlock<DiversityTrimming3Distribution> {
    DiversityTrimming3Distribution(ConditionalDistribution2<DiversitySegment, Integer, Integer, TrimmingDistribution, TwoSideTrimmingDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, TwoSideTrimmingDistribution::new, fromAccumulator);
    }

    private DiversityTrimming3Distribution(Map<DiversitySegment, TwoSideTrimmingDistribution> probabilityMap) {
        super(probabilityMap, TwoSideTrimmingDistribution::new, true);
    }

    public static DiversityTrimming3Distribution fromMap(
            Map<DiversitySegment, Map<Integer, Map<Integer, Double>>> probabilities) {
        return new DiversityTrimming3Distribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> TwoSideTrimmingDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public DiversityTrimming3Distribution copy(boolean fromAccumulator) {
        return new DiversityTrimming3Distribution(this, fromAccumulator);
    }
}
