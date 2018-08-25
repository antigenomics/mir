package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class JoiningGivenVariableSegmentDistribution
        extends ConditionalDistribution1<VariableSegment, JoiningSegment, JoiningSegmentDistribution> {
    public JoiningGivenVariableSegmentDistribution(Map<VariableSegment, JoiningSegmentDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
