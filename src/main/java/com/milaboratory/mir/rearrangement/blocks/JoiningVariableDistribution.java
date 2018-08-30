package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Map;

public class JoiningVariableDistribution
        extends ConditionalDistribution1<VariableSegment, JoiningSegment, JoiningDistribution> {
    public JoiningVariableDistribution(Map<VariableSegment, JoiningDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public static JoiningVariableDistribution fromMap(Map<VariableSegment, Map<JoiningSegment, Double>> probabilities) {
        return new JoiningVariableDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> JoiningDistribution.fromMap(e.getValue())
        ));
    }

    // todo: remove?
    public boolean isConsistent(VariableDistribution variableDistribution) {
        for (VariableSegment value : variableDistribution.getDistributionMap().values()) {
            if (getDistribution0(value) == null) {
                return false;
            }
        }
        return true;
    }
}
