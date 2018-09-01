package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class DiversityJoiningDistribution
        extends ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversityDistribution>
        implements ModelBlock<DiversityJoiningDistribution> {
    DiversityJoiningDistribution(ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversityDistribution> toCopy,
                                 boolean fromAccumulator) {
        super(toCopy, DiversityDistribution::new, fromAccumulator);
    }

    private DiversityJoiningDistribution(Map<JoiningSegment, DiversityDistribution> embeddedProbs) {
        super(embeddedProbs, DiversityDistribution::new, true);
    }

    public static DiversityJoiningDistribution fromMap(Map<JoiningSegment, Map<DiversitySegment, Double>> probabilities) {
        return new DiversityJoiningDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> DiversityDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public DiversityJoiningDistribution copy(boolean fromAccumulator) {
        return new DiversityJoiningDistribution(this, fromAccumulator);
    }
}