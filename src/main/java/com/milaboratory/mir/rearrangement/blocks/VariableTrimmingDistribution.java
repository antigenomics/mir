package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class VariableTrimmingDistribution
        extends ConditionalDistribution1<VariableSegment, Integer, TrimmingDistribution> {

    public VariableTrimmingDistribution(Map<VariableSegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
