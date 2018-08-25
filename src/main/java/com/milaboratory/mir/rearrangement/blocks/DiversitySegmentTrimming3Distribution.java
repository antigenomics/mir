package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;

import java.util.Map;

public class DiversitySegmentTrimming3Distribution
        extends ConditionalDistribution2<DiversitySegment, Integer, Integer,
        TrimmingDistribution, TwoSideSegmentTrimmingDistribution> {
    public DiversitySegmentTrimming3Distribution(Map<DiversitySegment,
            TwoSideSegmentTrimmingDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
