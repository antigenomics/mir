package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.generator.InsertSizeGenerator;
import com.milaboratory.mir.model.probability.ProbabilityUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class IntegerGeneratorImpl implements InsertSizeGenerator {
    private final int[] values;
    private final double[] probabilities;
    private final Random random;

    public IntegerGeneratorImpl(Map<String, Double> probabilityMap) {
        this(probabilityMap, ThreadLocalRandom.current());
    }

    public IntegerGeneratorImpl(Map<String, Double> probabilityMap,
                                Random random) {
        this.values = new int[probabilityMap.size()];
        this.probabilities = new double[probabilityMap.size()];
        this.random = random;

        var probabilityList = probabilityMap
                .entrySet()
                .stream()
                .sorted((o1, o2) -> -o1.getValue().compareTo(o2.getValue()))
                .collect(Collectors.toList());

        for (int i = 0; i < probabilityList.size(); i++) {
            var entry = probabilityList.get(i);
            values[i] = Integer.parseInt(entry.getKey());
            probabilities[i] = entry.getValue();
        }
    }

    @Override
    public int generate() {
        return values[ProbabilityUtils.sample(probabilities, random)];
    }
}
