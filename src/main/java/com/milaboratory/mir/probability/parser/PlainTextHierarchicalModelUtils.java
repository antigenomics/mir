package com.milaboratory.mir.probability.parser;

import java.util.HashMap;
import java.util.Map;

public final class PlainTextHierarchicalModelUtils {
    private PlainTextHierarchicalModelUtils() {
    }

    public static final String CONDITIONAL_SEPARATOR = "|",
            REGEX_CONDITIONAL_SEPARATOR = "\\|",
            VARIABLE_SEPARATOR = ",";

    public static Map<String, Map<String, Double>> embed1Conditional(Map<String, Double> probabilityMap) {
        return embed1(probabilityMap, CONDITIONAL_SEPARATOR);
    }

    public static Map<String, Map<String, Double>> embed1Joint(Map<String, Double> probabilityMap) {
        return embed1(probabilityMap, VARIABLE_SEPARATOR);
    }

    public static Map<String, Map<String, Double>> embed1(Map<String, Double> probabilityMap,
                                                          String separator) {
        var embeddedProbabilities = new HashMap<String, Map<String, Double>>();
        probabilityMap.forEach(
                (key, value) -> {
                    var variables = key.split(separator);
                    embeddedProbabilities
                            .computeIfAbsent(variables[1], x -> new HashMap<>())
                            .put(variables[0], value);
                }
        );
        return embeddedProbabilities;
    }

    public static Map<String, Map<String, Map<String, Double>>> embed2(Map<String, Double> probabilityMap) {
        var embeddedProbabilities = embed1(probabilityMap, CONDITIONAL_SEPARATOR);

        var embeddedProbabilities2 = new HashMap<String, Map<String, Map<String, Double>>>();
        embeddedProbabilities.forEach((key, value) -> {
                    var variables = key.split(VARIABLE_SEPARATOR);
                    embeddedProbabilities2
                            .computeIfAbsent(variables[1], x -> new HashMap<>())
                            .put(variables[0], embeddedProbabilities.get(key));
                }
        );

        return embeddedProbabilities2;
    }
}
