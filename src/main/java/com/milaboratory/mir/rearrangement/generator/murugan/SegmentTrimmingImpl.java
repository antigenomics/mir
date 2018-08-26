package com.milaboratory.mir.rearrangement.generator.murugan;

import com.milaboratory.mir.rearrangement.generator.SegmentTrimmingGenerator;
import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;
import com.milaboratory.mir.segment.Cdr3GermlineSegment;
import com.milaboratory.mir.segment.SegmentProvider;

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
        var embeddedProbabilities = HierarchicalModelFormula.embed1Conditional(probabilityMap);

        embeddedProbabilities.forEach((segmentId, trimmingProbMap) ->
                generatorMap.put(segmentProvider.fromId(segmentId), new IntegerGeneratorImpl(trimmingProbMap, random))
        );
    }

    @Override
    public int generate(T segmentId) {
        // todo: catch error
        return generatorMap.get(segmentId).generate();
    }

    @Override
    public double getProbability(int trimming, T segment) {
        var p1 = generatorMap.get(segment);
        if (p1 == null) {
            return 0;
        }
        var prob = p1.getProbabilities().get(trimming);
        return prob == null ? 0 : prob;
    }
}
