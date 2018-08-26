package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DiversityJoiningVariableDistribution
        extends ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment,
        DiversityDistribution, DiversityJoiningDistribution> {

    public DiversityJoiningVariableDistribution(Map<VariableSegment, DiversityJoiningDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public DiversityJoiningVariableDistribution(Map<VariableSegment, Map<JoiningSegment, Map<DiversitySegment, Double>>> probabilities,
                                                Random random) {
        this(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> new DiversityJoiningDistribution(e.getValue(), random)
        ));
    }
}
