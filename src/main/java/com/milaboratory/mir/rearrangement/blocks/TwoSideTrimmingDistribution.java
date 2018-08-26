package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;

import java.util.Map;

public class TwoSideTrimmingDistribution
        extends ConditionalDistribution1<Integer, Integer, TrimmingDistribution> {
    public TwoSideTrimmingDistribution(Map<Integer, TrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
