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
    private final Map<String, List<String>> graph = new HashMap<>();

    private static boolean goodFormula(String formula) {
        var c1 = formula.chars().filter(ch -> ch == 'P').count();
        var c2 = formula.chars().filter(ch -> ch == ')').count();
        var c3 = formula.chars().filter(ch -> ch == '(').count();

        return c1 == c2 && c2 == c3;
    }

    public static HierarchicalModelFormula fromString(String formula) {
        if (!goodFormula(formula)) {
            throw new IllegalArgumentException("Bad formula '" + formula + "'");
        }

        return new HierarchicalModelFormula(
                Arrays.stream(formula.replaceAll("P\\(", "").split("\\)"))
                        .collect(Collectors.toList())
        );
    }

    public HierarchicalModelFormula(List<String> blockNames) {
        for (String blockName : blockNames) {
            if (blockName.chars().filter(ch -> ch == '|').count() > 1) {
                throw new IllegalArgumentException("Bad block name: '" + blockName + "'. " +
                        "Only one '|' (conditional) symbol is allowed.");
            }

            String[] blockNameSplit = blockName.split(REGEX_CONDITIONAL_SEPARATOR);
            String variable = blockNameSplit[0].trim();
            if (variables.contains(variable)) {
                throw new IllegalArgumentException("Bad probability: " +
                        "creating duplicate probability distribution for '" +
                        variable + "' with '" + blockName + "'");
            }
            variables.add(variable);
            if (blockNameSplit.length > 1) {
                if (blockNameSplit[0].contains(VARIABLE_SEPARATOR)) {
                    throw new IllegalArgumentException("Cannot mix joint probability of '" +
                            blockNameSplit[0] + "' with conditioning on " +
                            blockNameSplit[1] + ". Any block should be either " +
                            "joint (multivariate) or univariate conditional or univariate unconditional.");
                }

                graph.put(variable,
                        Arrays.stream(blockNameSplit[1].split(VARIABLE_SEPARATOR))
                                .distinct() // make sure no duplicates
                                .map(String::trim)
                                .collect(Collectors.toList())
                );
            } else {
                graph.put(variable, Collections.emptyList());
            }
        }

        // Fetch clean block names and variables from graph
        this.blockNames = new HashSet<>();

        var variables = new ArrayList<String>();
        var jointVariables = new HashSet<>(); // for checking that we haven't messed up with
        // things like 'x,y|' and 'y,x|' both in the model
        graph.forEach((v, vc) -> {
            variables.add(v);
            if (vc.isEmpty()) {
                //this.blockNames.add(v);
                for (String vj : v.split(VARIABLE_SEPARATOR)) {
                    if (jointVariables.contains(vj)) {
                        throw new IllegalArgumentException("Bad block '" +
                                v + "': variable '" + vj + "' already listed " +
                                "as unconditional in other block.");
                    }
                    jointVariables.add(vj);
                }
            } else {
                //this.blockNames.add(v + CONDITIONAL_SEPARATOR +
                //        String.join(VARIABLE_SEPARATOR, vc));
                variables.addAll(vc);
            }
            this.blockNames.add(createSubFormula(Collections.singletonList(v), vc));
        });

        for (String variable : variables) {
            if (!this.variables.contains(variable)) {
                throw new IllegalArgumentException("Incomplete probability: missing distribution for variable" +
                        " '" + variable + "'");
            }
        }
    }

    public static String createSubFormula(List<String> variables, List<String> conditionals) {
        return String.join(VARIABLE_SEPARATOR, variables) +
                (conditionals.isEmpty() ? "" : CONDITIONAL_SEPARATOR + String.join(VARIABLE_SEPARATOR, conditionals));
    }

    public List<String> getParentVariables(String variable) {
        var parents = graph.get(variable);
        if (parents == null) {
            throw new IllegalArgumentException("No distribution for variable '" + variable + "' is found in formula.");
        }
        return Collections.unmodifiableList(parents);
    }

    public Set<String> getBlockNames() {
        return Collections.unmodifiableSet(blockNames);
    }

    public boolean hasVariable(String variable) {
        return variables.contains(variable);
    }

    public String findBlockName(String variable) {
        for (String blockName : blockNames) {
            if (blockName.contains(CONDITIONAL_SEPARATOR) ?
                    blockName.startsWith(variable) :
                    blockName.contains(variable)) {
                return blockName;
            }
        }
        return null;
    }

    public String getBlockName(String variable) {
        String res = findBlockName(variable);
        if (res == null) {
            throw new IllegalArgumentException("No distribution for variable '" + variable + "' is found in formula.");
        }
        return res;
    }

    public BlockNameInfo getBlockInfo(String variable) {
        return BlockNameInfo.fromBlockName(getBlockName(variable));
    }

    public Set<String> getVariables() {
        return Collections.unmodifiableSet(variables);
    }

    @Override
    public String toString() {
        return blockNames.stream().map(x -> "P(" + x + ")").collect(Collectors.joining());
    }
}
