package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class DiversityJoiningVariableDistribution
        extends ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment,
        DiversityDistribution, DiversityJoiningDistribution> {

    public DiversityJoiningVariableDistribution(Map<VariableSegment, DiversityJoiningDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
