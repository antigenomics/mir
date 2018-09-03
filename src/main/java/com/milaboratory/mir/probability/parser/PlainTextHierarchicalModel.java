package com.milaboratory.mir.probability.parser;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.StringArrayIndexer;

import java.util.*;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.CONDITIONAL_SEPARATOR;
import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.REGEX_CONDITIONAL_SEPARATOR;
import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.VARIABLE_SEPARATOR;

public class PlainTextHierarchicalModel {
    private final HierarchicalModelFormula formula;
    private final Map<String, Map<String, Double>> probabilities;

    public PlainTextHierarchicalModel(HierarchicalModelFormula formula,
                                      Map<String, Map<String, Double>> probabilities) {
        this.formula = formula;
        this.probabilities = new HashMap<>();
        probabilities.forEach((key, value) -> this.probabilities.put(key, Collections.unmodifiableMap(value)));
    }

    public HierarchicalModelFormula getFormula() {
        return formula;
    }

    //public Map<String, Map<String, Double>> getProbabilities() {
    //    return Collections.unmodifiableMap(probabilities);
    //}

    public Set<String> listValues(String variableOrBlockName) {
        return probabilities.get(formula.findBlockName(variableOrBlockName)).keySet();
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
            return map;
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
}
