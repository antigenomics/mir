package com.milaboratory.mir.probability.parser;

import java.util.*;


import static com.milaboratory.mir.CommonUtils.map2map;
import static com.milaboratory.mir.probability.ProbabilityMathUtils.sum;

public final class PlainTextHierarchicalModelUtils {
    private PlainTextHierarchicalModelUtils() {
    }

    public static final String CONDITIONAL_SEPARATOR = "|",
            REGEX_CONDITIONAL_SEPARATOR = "\\|",
            VARIABLE_SEPARATOR = ",";

    /////////// WORKING WITH CONDITIONAL PROBABILITIES //////////

    public static Map<String, Map<String, Double>> embed1Conditional(Map<String, Double> probabilityMap) {
        return embed1(probabilityMap, REGEX_CONDITIONAL_SEPARATOR);
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

    public static Map<String, Map<String, Map<String, Double>>> embed2Conditional(Map<String, Double> probabilityMap) {
        var embeddedProbabilities = embed1(probabilityMap, REGEX_CONDITIONAL_SEPARATOR);

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


    /////////// WORKING WITH JOINT PROBABILITIES //////////


    public static Map<String, Map<String, Double>> embed1Joint(Map<String, Double> probabilityMap) {
        return embed1(probabilityMap, VARIABLE_SEPARATOR);
    }

    public static JointProbabilityDecomposition decomposeEmbeddedJoint(Map<String, Map<String, Double>> jointProbabilityMap) {
        // todo: check that sums to 1
        var marginal = map2map(
                jointProbabilityMap,
                Map.Entry::getKey,
                e -> sum(e.getValue().values())
        );

        var conditional = map2map(
                jointProbabilityMap,
                Map.Entry::getKey,
                e -> {
                    double norm = marginal.get(e.getKey());
                    return map2map(
                            e.getValue(),
                            Map.Entry::getKey,
                            ee -> ee.getValue() / norm
                    );
                }
        );
        return new JointProbabilityDecomposition(marginal, conditional);
    }

    // proxy for embed + decompose
    public static JointProbabilityDecomposition decomposeJoint(Map<String, Double> probabilityMap) {
        return decomposeEmbeddedJoint(embed1Joint(probabilityMap));
    }

    public static class JointProbabilityDecomposition {
        private final Map<String, Double> marginal;
        private final Map<String, Map<String, Double>> conditional;

        public JointProbabilityDecomposition(Map<String, Double> marginal,
                                             Map<String, Map<String, Double>> conditional) {
            this.marginal = Collections.unmodifiableMap(marginal);
            this.conditional = new HashMap<>();
            conditional.forEach((k, v) ->
                    this.conditional.put(k, Collections.unmodifiableMap(v))
            );
        }

        public Map<String, Double> getMarginal() {
            return marginal;
        }

        public Map<String, Map<String, Double>> getConditional() {
            return Collections.unmodifiableMap(conditional);
        }
    }

    //////// OTHER UTILS //////

    public static Map<String, Map<String, Double>> mockConditional1(Set<String> values,
                                                                    Map<String, Double> probabilityMap) {
        var res = new HashMap<String, Map<String, Double>>();

        for (String value : values) {
            res.put(value, probabilityMap);
        }

        return res;
    }

    public static Map<String, Map<String, Map<String, Double>>> mockConditional2(Set<String> values,
                                                                                 Map<String, Map<String, Double>> probabilityMap) {
        var res = new HashMap<String, Map<String, Map<String, Double>>>();

        for (String value : values) {
            res.put(value, probabilityMap);
        }

        return res;
    }
}
