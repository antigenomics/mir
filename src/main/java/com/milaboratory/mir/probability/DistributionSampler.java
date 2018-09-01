package com.milaboratory.mir.probability;

import com.milaboratory.mir.pipe.Generator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DistributionSampler<T> implements Generator<T> {
    private final List<DistributionEntry<T>> sortedDistributionEntries;

    public static <T> DistributionSampler<T> getDummy() {
        return new DistributionSampler<>() {
            @Override
            public T generate() {
                throw new UnsupportedOperationException("Cannot sample from dummy distribution.");
            }
        };
    }

    private DistributionSampler() {
        this.sortedDistributionEntries = null;
    }

    public DistributionSampler(DistributionMap<T> distributionMap) {
        this(distributionMap.listEntries());
    }

    public DistributionSampler(List<DistributionEntry<T>> distributionEntries) {
        if (distributionEntries.isEmpty()) {
            throw new IllegalArgumentException("Empty list provided, no elements to sample.");
        }

        ProbabilityMathUtils.assertNormalized(
                distributionEntries.stream().map(DistributionEntry::getProbability).collect(Collectors.toList())
        );

        this.sortedDistributionEntries = distributionEntries
                .stream()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public T generate() {
        double p = ThreadLocalRandom.current().nextDouble();
        double sum = 0;

        for (DistributionEntry<T> distributionEntry : sortedDistributionEntries) {
            sum += distributionEntry.getProbability();
            if (sum >= p) {
                return distributionEntry.getValue();
            }
        }

        return sortedDistributionEntries.get(0).getValue();
    }
}
