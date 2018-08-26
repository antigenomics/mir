package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class JoiningVariableDistribution
        extends ConditionalDistribution1<VariableSegment, JoiningSegment, JoiningDistribution> {
    public JoiningVariableDistribution(Map<VariableSegment, JoiningDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
