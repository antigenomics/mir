package com.milaboratory.mir.rearrangement.generator.murugan;

import com.milaboratory.mir.rearrangement.generator.ConditionalSegmentGenerator;
import com.milaboratory.mir.rearrangement.probability.CategoricalProbabilityDistribution;
import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;
import com.milaboratory.mir.segment.Cdr3GermlineSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.SegmentType;
import com.milaboratory.mir.segment.provider.SegmentProvider;

import java.util.HashMap;
import java.util.Map;

public class ConditionalSegmentGeneratorImpl<T extends Cdr3GermlineSegment,
        V extends Cdr3GermlineSegment> implements ConditionalSegmentGenerator<T, V> {
    private final Map<V, CategoricalProbabilityDistribution<T>> categoricalProbabilityDistributionMap;
    private final Map<V, Map<T, Double>> probabilities;

    public ConditionalSegmentGeneratorImpl(SegmentLibrary segmentLibrary,
                                           SegmentType segmentTypeT, SegmentType segmentTypeV,
                                           Map<String, Double> probabilities) {
        this(segmentLibrary.asSegmentProvider(segmentTypeT),
                segmentLibrary.asSegmentProvider(segmentTypeV),
                probabilities);
    }

    public ConditionalSegmentGeneratorImpl(SegmentProvider<T> segmentProviderT,
                                           SegmentProvider<V> segmentProviderV,
                                           Map<String, Double> probabilityMap) {
        // un-flatten, cond segment V -> var segment T -> probability
        var embeddedProbabilities = HierarchicalModelFormula.embed1Conditional(probabilityMap);

        // create categorical probabilities and put them into map condition -> {value, probability}
        this.categoricalProbabilityDistributionMap = new HashMap<>();
        embeddedProbabilities.forEach(
                (key, value) ->
                        categoricalProbabilityDistributionMap.put(segmentProviderV.fromId(key),
                                SegmentGeneratorImpl.wrapToCPD(segmentProviderT, value))
        );

        this.probabilities = new HashMap<>();
        categoricalProbabilityDistributionMap.forEach((v, map) ->
                probabilities.put(v, map.asProbabilityMap())
        );
    }

    @Override
    public T generate(V segment) {
        // todo: catch error
        return categoricalProbabilityDistributionMap.get(segment).sample();
    }

    @Override
    public double getProbability(T segment, V conditionalSegment) {
        var p1 = probabilities.get(conditionalSegment);
        if (p1 == null) {
            return 0;
        }
        var p2 = p1.get(segment);
        return p2 == null ? 0 : p2;
    }
}
