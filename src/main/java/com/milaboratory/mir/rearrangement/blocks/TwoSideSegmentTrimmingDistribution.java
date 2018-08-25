package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;

import java.util.Map;

public class TwoSideSegmentTrimmingDistribution
        extends ConditionalDistribution1<Integer, Integer, TrimmingDistribution> {
    public TwoSideSegmentTrimmingDistribution(Map<Integer, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
