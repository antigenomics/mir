package com.milaboratory.mir.probability;

import com.milaboratory.mir.pipe.Generator;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.stream.Collectors;

public class DistributionSampler<T> implements Generator<T> {
    private final T[] values;
    private final double[] probabilities;
    private final Random random;

    @SuppressWarnings("unchecked")
    public DistributionSampler(DistributionMap<T> distributionMap,
                               Class<T> clazz,
                               Random random) {
        var sortedProbs = distributionMap
                .listEntries()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        this.values = (T[]) Array.newInstance(clazz, sortedProbs.size());
        this.probabilities = new double[sortedProbs.size()];
        this.random = random;
        for (int i = 0; i < sortedProbs.size(); i++) {
            var probEntry = sortedProbs.get(i);
            values[i] = probEntry.getValue();
            probabilities[i] = probEntry.getProbability();
        }
    }

    public DistributionSampler(T[] values,
                               double[] probabilities,
                               Random random) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Empty value array");
        }
        if (values.length != probabilities.length) {
            throw new IllegalArgumentException("Probability and value array lengths don't match");
        }
        this.values = values;
        this.probabilities = probabilities;
        this.random = random;
    }

    @Override
    public T generate() {
        return values[ProbabilityMathUtils.sample(probabilities, random)];
    }
}
