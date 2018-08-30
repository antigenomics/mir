package com.milaboratory.mir.probability.parser;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.REGEX_CONDITIONAL_SEPARATOR;
import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.CONDITIONAL_SEPARATOR;
import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.VARIABLE_SEPARATOR;

public final class HierarchicalModelFormula implements Serializable {
    private final Set<String> variables = new HashSet<>();
    private final Set<String> blockNames;
    private final Map<String, Set<String>> graph = new HashMap<>();

    public static HierarchicalModelFormula fromString(String formula) {
        return new HierarchicalModelFormula(
                Arrays.stream(formula.replaceAll("P\\(", "").split("\\)"))
                        .collect(Collectors.toList())
        );
    }

    public HierarchicalModelFormula(List<String> blockNames) {
        for (String blockName : blockNames) {
            String[] blockNameSplit = blockName.split(REGEX_CONDITIONAL_SEPARATOR);
            String variable = blockNameSplit[0].trim();
            if (variables.contains(variable)) {
                throw new IllegalArgumentException("Bad probability: " +
                        "creating duplicate probability distribution for '" +
                        variable + "' with '" + blockName + "'");
            }
            variables.add(variable);
            if (blockNameSplit.length > 1) {
                graph.put(variable,
                        Arrays.stream(blockNameSplit[1].split(VARIABLE_SEPARATOR))
                                .map(String::trim)
                                .collect(Collectors.toSet())
                );
            } else {
                graph.put(variable, Collections.emptySet());
            }
        }

        this.blockNames = new HashSet<>();

        var variables = new ArrayList<String>();
        graph.forEach((v, vc) -> {
            variables.add(v);
            if (vc.isEmpty()) {
                this.blockNames.add(v);
            } else {
                this.blockNames.add(v + CONDITIONAL_SEPARATOR +
                        String.join(VARIABLE_SEPARATOR, vc));
                variables.addAll(vc);
            }
        });

        for (String variable : variables) {
            if (!this.variables.contains(variable)) {
                throw new IllegalArgumentException("Incomplete probability: missing distribution for variable" +
                        " '" + variable + "'");
            }
        }
    }

    public Set<String> getParentVariables(String variable) {
        return Collections.unmodifiableSet(graph.get(variable));
    }

    public Collection<String> getBlockNames() {
        return Collections.unmodifiableSet(blockNames);
    }

    public boolean hasVariable(String variable) {
        return variables.contains(variable);
    }

    public String findBlockName(String variable) {
        for (String ip : blockNames) {
            if (ip.toLowerCase().startsWith(variable)) {
                return ip;
            }
        }
        return null;
    }

    public String getBlockName(String variable) {
        String res = findBlockName(variable);
        if (res == null) {
            throw new IllegalArgumentException("Variable '" + variable + "' not found in formula.");
        }
        return res;
    }

    public Collection<String> getVariables() {
        return Collections.unmodifiableSet(variables);
    }

    @Override
    public String toString() {
        return blockNames.stream().map(x -> "P(" + x + ")").collect(Collectors.joining());
    }
}
