package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.JoiningSegment;

import java.util.Map;

public class JoiningSegmentTrimmingDistribution
        extends ConditionalDistribution1<JoiningSegment, Integer, TrimmingDistribution> {

    public JoiningSegmentTrimmingDistribution(Map<JoiningSegment, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
