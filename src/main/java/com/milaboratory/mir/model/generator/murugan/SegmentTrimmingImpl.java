package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.generator.SegmentTrimmingGenerator;
import com.milaboratory.mir.model.probability.ProbabilisticModelFormula;
import com.milaboratory.mir.segment.Cdr3GermlineSegment;
import com.milaboratory.mir.segment.provider.SegmentProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SegmentTrimmingImpl<T extends Cdr3GermlineSegment> implements SegmentTrimmingGenerator<T> {
    private Map<T, IntegerGeneratorImpl> generatorMap;

    public SegmentTrimmingImpl(SegmentProvider<T> segmentProvider,
                               Map<String, Double> probabilityMap,
                               Random random) {
        this.generatorMap = new HashMap<>();

        // un-flatten, cond segment V -> var segment T -> probability
        var embeddedProbabilities = ProbabilisticModelFormula.embed1Conditional(probabilityMap);

        embeddedProbabilities.forEach((segmentId, trimmingProbMap) ->
                generatorMap.put(segmentProvider.fromId(segmentId), new IntegerGeneratorImpl(trimmingProbMap, random))
        );
    }

    @Override
    public int generate(T segmentId) {
        // todo: catch error
        return generatorMap.get(segmentId).generate();
    }
}
