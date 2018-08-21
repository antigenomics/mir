package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.generator.ConditionalSegmentGenerator;
import com.milaboratory.mir.model.parser.MuruganModelParser;
import com.milaboratory.mir.model.probability.CategoricalProbabilityDistribution;
import com.milaboratory.mir.segment.Cdr3GermlineSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.SegmentType;
import com.milaboratory.mir.segment.provider.SegmentProvider;

import java.util.HashMap;
import java.util.Map;

public class ConditionalSegmentGeneratorImpl<T extends Cdr3GermlineSegment,
        V extends Cdr3GermlineSegment> implements ConditionalSegmentGenerator<T, V> {
    private final Map<V, CategoricalProbabilityDistribution<T>> categoricalProbabilityDistributionMap;

    public ConditionalSegmentGeneratorImpl(SegmentLibrary segmentLibrary,
                                           SegmentType segmentTypeT, SegmentType segmentTypeV,
                                           Map<String, Double> probabilities) {
        this(segmentLibrary.asSegmentProvider(segmentTypeT),
                segmentLibrary.asSegmentProvider(segmentTypeV),
                probabilities);
    }

    public ConditionalSegmentGeneratorImpl(SegmentProvider<T> segmentProviderT,
                                           SegmentProvider<V> segmentProviderV,
                                           Map<String, Double> probabilities) {
        // un-flatten, condition -> value -> probability
        var embeddedProbabilities = new HashMap<String, Map<String, Double>>();
        probabilities.forEach(
                (key, value) -> {
                    var segmentPair = key.split(MuruganModelParser.CONDITIONAL_SEPARATOR);
                    embeddedProbabilities
                            .computeIfAbsent(segmentPair[1], x -> new HashMap<>())
                            .put(segmentPair[0], value);
                }
        );

        // create categorical probabilities and put them into map condition -> {value, probability}
        this.categoricalProbabilityDistributionMap = new HashMap<>();
        embeddedProbabilities.forEach(
                (key, value) ->
                        categoricalProbabilityDistributionMap.put(segmentProviderV.fromId(key),
                                SegmentGeneratorImpl.wrapToCPD(segmentProviderT, value))
        );
    }

    @Override
    public T generate(V segment) {
        return categoricalProbabilityDistributionMap.get(segment).sample();
    }
}
