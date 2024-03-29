package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.probability.ConditionalDistribution1;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;

import java.util.Map;

public class JoiningVariableDistribution
        extends ConditionalDistribution1<VariableSegment, JoiningSegment, JoiningDistribution>
        implements ModelBlock<JoiningVariableDistribution> {
    JoiningVariableDistribution(ConditionalDistribution1<VariableSegment, JoiningSegment, JoiningDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, JoiningDistribution::new, fromAccumulator);
    }

    private JoiningVariableDistribution(Map<VariableSegment, JoiningDistribution> probabilityMap) {
        super(probabilityMap, JoiningDistribution::new, true);
    }

    public static JoiningVariableDistribution fromMap(Map<VariableSegment, Map<JoiningSegment, Double>> probabilities) {
        return new JoiningVariableDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> JoiningDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public JoiningVariableDistribution copy(boolean fromAccumulator) {
        return new JoiningVariableDistribution(this, fromAccumulator);
    }
}
