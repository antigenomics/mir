package com.milaboratory.mir.model.probability;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlainTextProbabilisticModel {
    private final ProbabilisticModelFormula formula;
    private final Map<String, Map<String, Double>> probabilities;

    public PlainTextProbabilisticModel(ProbabilisticModelFormula formula,
                                       Map<String, Map<String, Double>> probabilities) {
        this.formula = formula;
        this.probabilities = new HashMap<>();
        probabilities.forEach((key, value) -> {
            this.probabilities.put(key, Collections.unmodifiableMap(value));
        });
    }

    public ProbabilisticModelFormula getFormula() {
        return formula;
    }

    public Map<String, Map<String, Double>> getProbabilities() {
        return Collections.unmodifiableMap(probabilities);
    }
}
