package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;
import java.util.Random;

public class DiversityJoiningVariableDistribution
        extends ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment,
        DiversityDistribution, DiversityJoiningDistribution> {

    public DiversityJoiningVariableDistribution(Map<VariableSegment, DiversityJoiningDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public DiversityJoiningVariableDistribution fromMap(
            Map<VariableSegment, Map<JoiningSegment, Map<DiversitySegment, Double>>> probabilities) {
        return new DiversityJoiningVariableDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> DiversityJoiningDistribution.fromMap(e.getValue())
        ));
    }
}
