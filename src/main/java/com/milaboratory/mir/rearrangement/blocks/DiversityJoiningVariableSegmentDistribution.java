package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class DiversityJoiningVariableSegmentDistribution
        extends ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment,
        DiversitySegmentDistribution, DiversityJoiningSegmentDistribution> {

    public DiversityJoiningVariableSegmentDistribution(Map<VariableSegment, DiversityJoiningSegmentDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
