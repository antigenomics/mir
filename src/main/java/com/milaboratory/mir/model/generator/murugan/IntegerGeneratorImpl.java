package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.probability.ProbabilityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class IntegerGeneratorImpl {
    private final int[] values;
    private final double[] probabilityValues;
    private final Random random;
    protected final Map<Integer, Double> probabilities; // todo: can have faster impl

    public IntegerGeneratorImpl(Map<String, Double> probabilityMap) {
        this(probabilityMap, ThreadLocalRandom.current());
    }

    public IntegerGeneratorImpl(Map<String, Double> probabilityMap,
                                Random random) {
        this.values = new int[probabilityMap.size()];
        this.probabilityValues = new double[probabilityMap.size()];
        this.random = random;

        var probabilityList = probabilityMap
                .entrySet()
                .stream()
                .sorted((o1, o2) -> -o1.getValue().compareTo(o2.getValue()))
                .collect(Collectors.toList());

        this.probabilities = new HashMap<>();
        for (int i = 0; i < probabilityList.size(); i++) {
            var entry = probabilityList.get(i);
            int value = Integer.parseInt(entry.getKey());
            values[i] = value;
            probabilityValues[i] = entry.getValue();
            probabilities.put(value, entry.getValue());
        }
    }

    public int generate() {
        return values[ProbabilityUtils.sample(probabilityValues, random)];
    }

    Map<Integer, Double> getProbabilities() {
        return probabilities;
    }
}
