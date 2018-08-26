package com.milaboratory.mir.probability.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlainTextHierarchicalModel {
    private final HierarchicalModelFormula formula;
    private final Map<String, Map<String, Double>> probabilities;

    public PlainTextHierarchicalModel(HierarchicalModelFormula formula,
                                      Map<String, Map<String, Double>> probabilities) {
        this.formula = formula;
        this.probabilities = new HashMap<>();
        probabilities.forEach((key, value) -> {
            this.probabilities.put(key, Collections.unmodifiableMap(value));
        });
    }

    public HierarchicalModelFormula getFormula() {
        return formula;
    }

    //public Map<String, Map<String, Double>> getProbabilities() {
    //    return Collections.unmodifiableMap(probabilities);
    //}

    public Set<String> getBlockNames() {
        return probabilities.keySet();
    }

    public Map<String, Double> getProbabilityMap(String blockName) {
        var map = probabilities.get(blockName);

        if (map == null) {
            throw new IllegalArgumentException("Unknown model block " + blockName);
        }

        return map;
    }
}
