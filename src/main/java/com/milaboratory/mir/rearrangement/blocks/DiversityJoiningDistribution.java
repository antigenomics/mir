package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class DiversityJoiningDistribution
        extends ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversityDistribution> {
    public DiversityJoiningDistribution(Map<JoiningSegment, DiversityDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
