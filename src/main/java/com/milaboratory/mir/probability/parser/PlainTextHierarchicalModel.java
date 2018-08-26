package com.milaboratory.mir.probability.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Map<String, Double>> getProbabilities() {
        return Collections.unmodifiableMap(probabilities);
    }
}