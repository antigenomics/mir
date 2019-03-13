package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.probability.ConditionalDistribution2;
import com.antigenomics.mir.segment.DiversitySegment;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;

import java.util.Map;

public class DiversityJoiningVariableDistribution
        extends ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment,
        DiversityDistribution, DiversityJoiningDistribution>
        implements ModelBlock<DiversityJoiningVariableDistribution> {
    DiversityJoiningVariableDistribution(ConditionalDistribution2<VariableSegment, JoiningSegment, DiversitySegment, DiversityDistribution, DiversityJoiningDistribution> toCopy,
                                         boolean fromAccumulator) {
        super(toCopy, DiversityJoiningDistribution::new, fromAccumulator);
    }

    private DiversityJoiningVariableDistribution(Map<VariableSegment, DiversityJoiningDistribution> probabilityMap) {
        super(probabilityMap, DiversityJoiningDistribution::new, true);
    }

    public static DiversityJoiningVariableDistribution fromMap(
            Map<VariableSegment, Map<JoiningSegment, Map<DiversitySegment, Double>>> probabilities) {
        return new DiversityJoiningVariableDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> DiversityJoiningDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public DiversityJoiningVariableDistribution copy(boolean fromAccumulator) {
        return new DiversityJoiningVariableDistribution(this, fromAccumulator);
    }
}
