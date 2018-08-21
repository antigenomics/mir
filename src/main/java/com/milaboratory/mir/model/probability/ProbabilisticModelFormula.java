package com.milaboratory.mir.model.probability;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public final class ProbabilisticModelFormula implements Serializable {
    public static final String CONDITIONAL_SEPARATOR = "|",
            REGEX_CONDITIONAL_SEPARATOR = "\\|",
            VARIABLE_SEPARATOR = ",";

    private final Set<String> independentDistributionNames;
    private final Map<String, Set<String>> graph = new HashMap<>();
    private final Set<String> variables = new HashSet<>();

    public static ProbabilisticModelFormula fromString(String formula) {
        return new ProbabilisticModelFormula(
                Arrays.stream(formula.replaceAll("P\\(", "").split("\\)"))
                        .collect(Collectors.toList())
        );
    }

    public ProbabilisticModelFormula(List<String> independentDistributionNames) {
        for (String pfull : independentDistributionNames) {
            String[] pfullSplit = pfull.split(REGEX_CONDITIONAL_SEPARATOR);
            String variable = pfullSplit[0].trim();
            if (variables.contains(variable)) {
                throw new IllegalArgumentException("Bad probability: creating duplicate probability distribution for " +
                        variable + " by " + pfull);
            }
            variables.add(variable);
            if (pfullSplit.length > 1) {
                graph.put(variable,
                        Arrays.stream(pfullSplit[1].split(VARIABLE_SEPARATOR))
                                .map(String::trim)
                                .collect(Collectors.toSet())
                );
            } else {
                graph.put(variable, Collections.emptySet());
            }
        }

        this.independentDistributionNames = new HashSet<>();
        var allVariables = new ArrayList<String>();

        graph.forEach((v, vc) -> {
            allVariables.add(v);
            if (vc.isEmpty()) {
                this.independentDistributionNames.add(v);
            } else {
                this.independentDistributionNames.add(v + CONDITIONAL_SEPARATOR +
                        String.join(VARIABLE_SEPARATOR, vc));
                allVariables.addAll(vc);
            }
        });

        for (String var : allVariables) {
            if (!variables.contains(var)) {
                throw new IllegalArgumentException("Incomplete probability: missing distribution for " + var);
            }
        }
    }

    public Set<String> getParentVariables(String variable) {
        return Collections.unmodifiableSet(graph.get(variable));
    }

    public Collection<String> getIndependentDistributionNames() {
        return Collections.unmodifiableSet(independentDistributionNames);
    }

    public boolean hasVariable(String variable) {
        return variables.contains(variable);
    }

    public String getParentDistributionName(String variable) {
        for (String ip : independentDistributionNames) {
            if (ip.toLowerCase().startsWith(variable)) {
                return ip;
            }
        }
        return null;
    }

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
                    var segmentPair = key.split(separator);
                    embeddedProbabilities
                            .computeIfAbsent(segmentPair[1], x -> new HashMap<>())
                            .put(segmentPair[0], value);
                }
        );
        return embeddedProbabilities;
    }

    public Collection<String> getVariables() {
        return Collections.unmodifiableSet(variables);
    }

    @Override
    public String toString() {
        return independentDistributionNames.stream().map(x -> "P(" + x + ")").collect(Collectors.joining());
    }
}
