package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DiversityJoiningDistribution
        extends ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversityDistribution> {
    public DiversityJoiningDistribution(Map<JoiningSegment, DiversityDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public DiversityJoiningDistribution(Map<JoiningSegment, Map<DiversitySegment, Double>> probabilities,
                                        Random random) {
        this(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> new DiversityDistribution(e.getValue(), random)
        ));
    }
}
