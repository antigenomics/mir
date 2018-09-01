package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1Factory;
import com.milaboratory.mir.probability.ConditionalDistribution2;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

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
