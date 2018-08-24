package com.milaboratory.mir.model.generator.murugan;

import com.milaboratory.mir.model.generator.InsertSizeGenerator;

import java.util.Map;
import java.util.Random;

public class InsertSizeGeneratorImpl extends IntegerGeneratorImpl implements InsertSizeGenerator {
    public InsertSizeGeneratorImpl(Map<String, Double> probabilityMap, Random random) {
        super(probabilityMap, random);
    }

    @Override
    public double getProbability(int value) {
        var prob = probabilities.get(value);
        return prob == null ? 0 : prob;
    }
}
