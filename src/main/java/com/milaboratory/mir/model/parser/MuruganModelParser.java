package com.milaboratory.mir.model.parser;

import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.model.probability.PlainTextProbabilisticModel;
import com.milaboratory.mir.model.probability.ProbabilisticModelFormula;
import com.milaboratory.mir.model.probability.ProbabilityUtils;

import java.io.*;
import java.util.*;

final class MuruganModelParser {
    private MuruganModelParser() {

    }

    public static MuruganModel load(InputStream params, InputStream marginals,
                                    ProbabilisticModelFormula formula,
                                    Species species, Gene gene)
            throws IOException {
        var valueIndexMap = readValueIndexMap(params, formula, true);
        var probabilities = readProbabilities(marginals, formula, valueIndexMap,
                true,
                true);
        return new MuruganModel(formula, probabilities, species, gene);
    }

    private static Map<String, Map<Integer, String>> readValueIndexMap(InputStream paramsInputStream,
                                                                       ProbabilisticModelFormula formula,
                                                                       boolean fixDinucl)
            throws IOException {
        // Initialize parameter value index map
        var valueIndexMap = new HashMap<String, Map<Integer, String>>();
        for (String name : formula.getVariables()) {
            valueIndexMap.put(name, new HashMap<>());
        }

        // Fill it from file
        try (BufferedReader br = new BufferedReader(new InputStreamReader(paramsInputStream))) {
            String variable = null;

            for (String line; (line = br.readLine()) != null; ) {
                if (line.startsWith("#")) {
                    // parse param name
                    var splitString = line.substring(1).split(";");
                    variable = splitString[splitString.length - 1].trim();

                    // check if we need this param
                    if (!formula.hasVariable(variable)) {
                        variable = null;
                    }
                } else if (variable != null) {
                    if (line.startsWith("%")) {
                        // value index entry
                        var splitString = line.substring(1).split(";");
                        String value = splitString[0].trim();
                        int valueIndex = Integer.parseInt(splitString[splitString.length - 1].trim());
                        valueIndexMap.get(variable).put(valueIndex, value);
                    } else {
                        // no value and no parameter name in this entry - spurious part so wait for new param name/etc
                        variable = null;
                    }
                }
            }
        }

        // Check that we have values for all variables
        valueIndexMap.forEach((key, value) -> {
            if (value.isEmpty()) {
                throw new RuntimeException("Failed to load indexing for variable " + key);
            }
        });

        // Fix dinucleotide naming, original Igor files only have 4 base indices while there should be 16
        if (fixDinucl) {
            for (String variable : formula.getVariables()) {
                if (variable.toLowerCase().contains("dinucl")) {
                    var fixedIndexMap = dinuclFix(valueIndexMap.get(variable));
                    valueIndexMap.put(variable, fixedIndexMap);
                }
            }
        }

        return valueIndexMap;
    }

    private static Map<Integer, String> dinuclFix(Map<Integer, String> originalMap) {
        Map<Integer, String> results = new HashMap<>();
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                results.put(k, originalMap.get(i) + originalMap.get(j));
                k++;
            }
        }
        return results;
    }

    private static Map<String, Map<String, Double>> readProbabilities(InputStream marginalsInputStream,
                                                                      ProbabilisticModelFormula formula,
                                                                      Map<String, Map<Integer, String>> valueIndexMap,
                                                                      boolean fixDinucl,
                                                                      boolean maskSecondaryAlleles)
            throws IOException {
        // initialize probabilities & check that we have all param values
        var probabilityMap = new HashMap<String, Map<String, Double>>();
        for (String name : formula.getIndependentDistributionNames()) {
            probabilityMap.put(name, new HashMap<>());
        }

        // Fill it from file
        try (BufferedReader br = new BufferedReader(new InputStreamReader(marginalsInputStream))) {
            String firstVariable = null;
            String parentDistributionName = null;
            Set<String> parentVariables = null;
            Map<String, Integer> parentVariableValueIndices = null;

            for (String line; (line = br.readLine()) != null; ) {
                if (line.startsWith("@")) { // Introducing variable described by following (conditional) distribution
                    firstVariable = line.substring(1).trim();
                    if (!formula.hasVariable(firstVariable)) {
                        // variable is unused in our probability, just discard it
                        firstVariable = null;
                    } else {
                        // also fetch parent variables and distribution name once
                        parentDistributionName = formula.getParentDistributionName(firstVariable);
                        parentVariables = formula.getParentVariables(firstVariable);

                        // So '#' line can be omitted if no parent variables
                        if (parentVariables.isEmpty()) {
                            parentVariableValueIndices = new HashMap<>();
                        }
                    }
                } else if (firstVariable != null) {
                    if (line.startsWith("#")) { // Introducing conditional dependence
                        // [value,index],... list for parent variable indices
                        var splitString = line.substring(1).split("],?");
                        parentVariableValueIndices = new HashMap<>();
                        for (String token : splitString) {
                            if (!token.isEmpty()) {
                                var splitString2 = token.split(",");
                                parentVariableValueIndices.put(
                                        splitString2[0].substring(1).trim(),
                                        Integer.parseInt(splitString2[1].trim()));
                            }
                        }

                        // Check for missing parent variables
                        var missingParents = new HashSet<>(parentVariables);
                        missingParents.removeAll(parentVariableValueIndices.keySet());
                        if (!missingParents.isEmpty()) {
                            throw new RuntimeException("Missing required parent variables for '" +
                                    firstVariable + "': " +
                                    String.join(",", missingParents));
                        }

                        // Check for extra parent variables
                        var extraParents = new HashSet<>(parentVariableValueIndices.keySet());
                        extraParents.removeAll(parentVariables);
                        if (!extraParents.isEmpty()) {
                            throw new RuntimeException("Extra parent variables for '" +
                                    firstVariable + "'detected in import data: " +
                                    String.join(",", extraParents));
                        }
                    } else if (line.startsWith("%")) { // Probability values
                        if (parentVariableValueIndices == null) {
                            throw new RuntimeException("No values of conditional variables given " +
                                    "prior to listing probability values for '" + firstVariable + "'.");
                        }

                        // Form variable values to be used as a key
                        // here we will only use conditional ones
                        StringBuilder variableSuffix = new StringBuilder();
                        parentVariableValueIndices.forEach((parentVariable, index) -> {
                            //int index = parentVariableValueIndices.get(parentVariable);
                            String value = valueIndexMap.get(parentVariable).get(index);
                            if (variableSuffix.length() == 0) {
                                variableSuffix.append("|").append(value);
                            } else {
                                variableSuffix.append(",").append(value);
                            }
                        });

                        double[] probabilities = Arrays.stream(line.substring(1).split(","))
                                .mapToDouble(Double::parseDouble).toArray();

                        if (firstVariable.toLowerCase().contains("dinucl") && fixDinucl) {
                            // todo: perhaps needs to be fixed
                            // will get sum to 1 for each row; we'll assume that its P(nt2|nt1)
                            // as we have no P(nt1), we'll just divide it by 4 to obtain P(nt2,nt1)=P(nt2|nt1)P(nt1)
                            // this is similar to assuming that first base is added at random
                            probabilities = ProbabilityUtils.normalize(probabilities);
                        }

                        if (!ProbabilityUtils.isNormalized(probabilities)) {
                            throw new RuntimeException("Probabilities for '" + firstVariable +
                                    "' conditional on '" + variableSuffix + "'" +
                                    " are not normalized (sum=" + ProbabilityUtils.sum(probabilities) +
                                    " with 1e-5 precision).");
                        }

                        if (maskSecondaryAlleles) {
                            probabilities = maskSecondayAlleles(valueIndexMap.get(firstVariable), probabilities);
                        }

                        for (int i = 0; i < probabilities.length; i++) {
                            double prob = probabilities[i];
                            if (prob > 0) {
                                String variable = valueIndexMap.get(firstVariable).get(i) + variableSuffix;
                                probabilityMap.get(parentDistributionName).put(variable, probabilities[i]);
                            }
                        }
                    }
                }
            }
        }

        return probabilityMap;
    }

    private static boolean isPrimaryAllele(String segmentId) {
        return !segmentId.contains("*") || segmentId.contains("*00") || segmentId.contains("*01");
    }

    private static double[] maskSecondayAlleles(Map<Integer, String> indexedValues, double[] probs) {
        double[] newProbs = new double[probs.length];
        for (int i = 0; i < probs.length; i++) {
            newProbs[i] = isPrimaryAllele(indexedValues.get(i)) ? probs[i] : 0;
        }
        return ProbabilityUtils.normalize(newProbs);
    }
}
