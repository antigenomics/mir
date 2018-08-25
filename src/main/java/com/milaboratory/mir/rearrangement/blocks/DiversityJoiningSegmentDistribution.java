package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class DiversityJoiningSegmentDistribution
        extends ConditionalDistribution1<JoiningSegment, DiversitySegment, DiversitySegmentDistribution> {
    public DiversityJoiningSegmentDistribution(Map<JoiningSegment, DiversitySegmentDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
