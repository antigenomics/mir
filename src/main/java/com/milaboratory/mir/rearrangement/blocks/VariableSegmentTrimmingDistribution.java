package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class VariableSegmentTrimmingDistribution
        extends ConditionalDistribution1<VariableSegment, Integer, TrimmingDistribution> {

    public VariableSegmentTrimmingDistribution(Map<VariableSegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
