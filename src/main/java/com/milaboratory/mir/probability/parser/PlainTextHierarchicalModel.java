package com.milaboratory.mir.probability.parser;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.StringArrayIndexer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.*;

public class PlainTextHierarchicalModel {
    private final HierarchicalModelFormula formula;
    private final Map<String, Map<String, Double>> probabilities;

    public PlainTextHierarchicalModel(Map<String, Map<String, Double>> probabilities) {
        this(probabilities, new HierarchicalModelFormula(new ArrayList<>(probabilities.keySet())));
    }

    public PlainTextHierarchicalModel(Map<String, Map<String, Double>> probabilities,
                                      HierarchicalModelFormula formula) {
        this.formula = formula;
        this.probabilities = new HashMap<>();
        probabilities.forEach((key, value) -> this.probabilities.put(key, Collections.unmodifiableMap(value)));
    }

    public HierarchicalModelFormula getFormula() {
        return formula;
    }

    Map<String, Map<String, Double>> getProbabilities() {
        return Collections.unmodifiableMap(probabilities);
    }

    public Set<String> listValues(String variableName) {
        var blockInfo = formula.getBlockInfo(variableName);

        int variableIndex = blockInfo.getVariables().indexOf(variableName);

        var values = new HashSet<String>();

        for (Map.Entry<String, Double> entry : probabilities.get(blockInfo.getBlockName()).entrySet()) {
            var valueBlockInfo = BlockNameInfo.fromBlockName(entry.getKey());
            values.add(valueBlockInfo.getVariables().get(variableIndex));
        }

        return values;
    }

    public Set<String> getBlockNames() {
        return probabilities.keySet();
    }

    public Map<String, Double> getProbabilityMap(String blockName) {
        var map = probabilities.get(blockName);

        if (map == null) {
            // try rescue in case variables are in wrong order
            var blockNameInfo = BlockNameInfo.fromBlockName(blockName);
            boolean conditional = blockNameInfo.isConditional();
            var variable = blockNameInfo.getVariables().get(0);
            var originalBlock = formula.getBlockName(variable); // parent block of a given variable
            var originalProbabilities = probabilities.get(originalBlock);

            List<String> originalOrder, requestedOrder;

            if (conditional) {
                originalOrder = formula.getParentVariables(variable);
                requestedOrder = blockNameInfo.getParentVariables();
            } else {
                originalOrder = Arrays.asList(originalBlock.split(VARIABLE_SEPARATOR));
                requestedOrder = blockNameInfo.getVariables();
            }

            if (!CommonUtils.matchIgnoreOrder(originalOrder, requestedOrder)) {
                throw new IllegalArgumentException("Unable to fetch requested distribution for model block '" +
                        blockName + "'. Variables don't match: " +
                        "original=[" + originalOrder + "] requested=[" + requestedOrder + "]");
            }

            var indexing = new StringArrayIndexer(originalOrder, false);

            return CommonUtils.map2map(
                    originalProbabilities,
                    e -> reorder(e.getKey(), indexing, requestedOrder, conditional),
                    Map.Entry::getValue
            );
        } else {
            return Collections.unmodifiableMap(map);
        }
    }

    private static String reorder(String unorderedString,
                                  StringArrayIndexer indexing,
                                  List<String> requestedOrder,
                                  boolean conditional) {
        return conditional ?
                reorderConditional(unorderedString, indexing, requestedOrder) :
                reorderVariables(unorderedString, indexing, requestedOrder);
    }

    private static String reorderConditional(String unorderedString,
                                             StringArrayIndexer indexing,
                                             List<String> requestedOrder) {
        var splitUnorderedString = unorderedString.split(REGEX_CONDITIONAL_SEPARATOR);

        return splitUnorderedString[0] + // reorder everything except first variable
                CONDITIONAL_SEPARATOR +
                reorderVariables(splitUnorderedString[1], indexing, requestedOrder);
    }

    private static String reorderVariables(String unorderedString,
                                           StringArrayIndexer indexing,
                                           List<String> requestedOrder) {
        return String.join(VARIABLE_SEPARATOR,
                indexing.reorder(unorderedString.split(VARIABLE_SEPARATOR), requestedOrder));
    }

    public double getProbability(List<String> values, List<String> parentValues, String blockName) {
        var blockNameInfo = BlockNameInfo.fromBlockName(blockName);
        if (blockNameInfo.getVariables().size() != values.size() ||
                blockNameInfo.getParentVariables().size() != parentValues.size()) {
            throw new IllegalArgumentException("Difference in number of (parent) variables " +
                    "between provided value lists and block formula.");
        }
        return probabilities
                .get(blockName)
                .get(HierarchicalModelFormula.createSubFormula(values, parentValues));
    }

    public Map<String, Double> computeMarginal(String variableName) {
        var blockInfo = formula.getBlockInfo(variableName);

        if (blockInfo.isConditional()) {
            // compute all parent marginals, store them in a variable name -> variable value -> prob map
            var parentMarginals = new HashMap<String, Map<String, Double>>();
            for (String parentVariable : blockInfo.getParentVariables()) {
                parentMarginals.put(parentVariable, computeMarginal(parentVariable));
            }

            // marginals for a given variable
            var newProbabilities = new HashMap<String, Double>();
            for (Map.Entry<String, Double> entry : probabilities.get(blockInfo.getBlockName()).entrySet()) {
                // parse value block to split variable/parent variable values
                var valueBlockInfo = BlockNameInfo.fromBlockName(entry.getKey());

                // conditional probability
                double probability = entry.getValue();

                // multiply by marginal probability of all parent variables
                for (int i = 0; i < blockInfo.getParentVariables().size(); i++) {
                    String parentVariableName = blockInfo.getParentVariables().get(i);
                    String parentVariableValue = valueBlockInfo.getParentVariables().get(i);
                    probability *= parentMarginals
                            .getOrDefault(parentVariableName, Collections.emptyMap())
                            .getOrDefault(parentVariableValue, 0d);
                }

                // append probability
                var value = HierarchicalModelFormula.createSubFormula(valueBlockInfo.getVariables(),
                        Collections.emptyList());
                newProbabilities.put(value, newProbabilities.getOrDefault(value, 0d) + probability);
            }
            return newProbabilities;
        } else {
            return new HashMap<>(probabilities.get(blockInfo.getBlockName()));
        }
    }

    // Removes all cases of missing distributions for certain combination of conditionals
    // also normalizes the distribution
    public PlainTextHierarchicalModel fortify() {
        Map<String, Map<String, Double>> newProbabilities = new HashMap<>();

        for (String blockName : formula.getBlockNames()) {
            var blockProbabilities = new HashMap<>(probabilities.get(blockName));
            var blockNameInfo = BlockNameInfo.fromBlockName(blockName);

            if (blockNameInfo.isConditional()) {
                // all values of a given variable
                // NB only univariate conditional is enforced in formula impl
                String variableName = blockNameInfo.getVariables().get(0);
                Set<String> values = listValues(variableName);

                // all combinations of values of parent variables
                List<String> parentValuesCombinations = new ArrayList<>();
                for (String parentVariable : blockNameInfo.getParentVariables()) {
                    parentValuesCombinations = combine(parentValuesCombinations,
                            listValues(parentVariable));
                }

                Map<String, Double> marginals = null;

                // iterate over probabilities grouped by parent variable combination
                for (String parentValues : parentValuesCombinations) {
                    var groupProbabilities = new HashMap<String, Double>();

                    double sum = 0;
                    // go through values of a given variable
                    for (String value : values) {
                        String fullValue = String.join(CONDITIONAL_SEPARATOR, value, parentValues);
                        // try find probability for this combination in original plain text model
                        // here we also fill gaps with 0s
                        double probability = blockProbabilities.getOrDefault(fullValue, 0d);
                        groupProbabilities.put(fullValue, probability);
                        sum += probability;
                    }

                    // No matches for a given combination - replace with marginal probs
                    if (sum == 0) {
                        if (marginals == null) { // lazy compute
                            marginals = computeMarginal(variableName);
                        }
                        for (Map.Entry<String, Double> entry : marginals.entrySet()) {
                            groupProbabilities.put(
                                    String.join(CONDITIONAL_SEPARATOR, entry.getKey(), parentValues),
                                    entry.getValue()
                            );
                        }
                    }

                    newProbabilities
                            .computeIfAbsent(blockName, x -> new HashMap<>())
                            // Append normalized probabilities for the group
                            .putAll(normalize(groupProbabilities));
                }
            } else {
                // Just renormalize
                newProbabilities.put(blockName, normalize(blockProbabilities));
            }
        }

        return new PlainTextHierarchicalModel(newProbabilities, formula);
    }

    private List<String> combine(Collection<String> first, Collection<String> second) {
        List<String> combinations = new ArrayList<>(first.size() * second.size());
        if (first.isEmpty()) {
            return new ArrayList<>(second);
        }
        for (String f : first) {
            for (String s : second) {
                combinations.add(String.join(VARIABLE_SEPARATOR, f, s));
            }
        }
        return combinations;
    }

    public static PlainTextHierarchicalModel fromString(String model) throws IOException {
        return PlainTextModelIO.readModel(new ByteArrayInputStream(model.getBytes()));
    }

    public static String toString(PlainTextHierarchicalModel model) {
        var os = new ByteArrayOutputStream();
        PlainTextModelIO.writeModel(model, os);
        return os.toString();
    }
}