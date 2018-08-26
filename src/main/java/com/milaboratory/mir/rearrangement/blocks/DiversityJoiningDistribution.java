package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class DiversityJoiningDistribution
        extends ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversityDistribution> {
    public DiversityJoiningDistribution(Map<JoiningSegment, DiversityDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static DiversityJoiningDistribution fromMap(Map<JoiningSegment, Map<DiversitySegment, Double>> probabilities) {
        return new DiversityJoiningDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> DiversityDistribution.fromMap(e.getValue())
        ));
    }
}
