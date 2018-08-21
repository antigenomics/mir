package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.generator.SegmentGenerator;
import com.milaboratory.mir.model.probability.CategoricalProbabilityDistribution;
import com.milaboratory.mir.model.probability.CategoryProbability;
import com.milaboratory.mir.segment.Cdr3GermlineSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.SegmentType;
import com.milaboratory.mir.segment.provider.SegmentProvider;

import java.util.Map;
import java.util.stream.Collectors;

public class SegmentGeneratorImpl<T extends Cdr3GermlineSegment> implements SegmentGenerator<T> {
    private final CategoricalProbabilityDistribution<T> categoricalProbabilityDistribution;

    public SegmentGeneratorImpl(SegmentLibrary segmentLibrary,
                                SegmentType segmentType,
                                Map<String, Double> probabilities) {
        this(segmentLibrary.asSegmentProvider(segmentType), probabilities);
    }

    public SegmentGeneratorImpl(SegmentProvider<T> segmentProvider,
                                Map<String, Double> probabilities) {
        this.categoricalProbabilityDistribution = wrapToCPD(segmentProvider,
                probabilities);
    }

    public static <T extends Cdr3GermlineSegment> CategoricalProbabilityDistribution<T> wrapToCPD(
            SegmentProvider<T> segmentProvider,
            Map<String, Double> probabilities) {
        return new CategoricalProbabilityDistribution<>(
                probabilities.entrySet().stream().map(x ->
                        new CategoryProbability<>(segmentProvider.fromId(x.getKey()), x.getValue())
                ).collect(Collectors.toList())
        );
    }

    @Override
    public T generate() {
        return categoricalProbabilityDistribution.sample();
    }
}
