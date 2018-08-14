package com.milaboratory.mir.io.model;

import org.junit.Test;

import java.io.*;
import java.util.*;

public class IgorModelParserTest {
    @Test
    public void run() throws IOException {
        String paramsPath = "src/main/resources/human_tra_model_params.txt",
                marginalsPath = "src/main/resources/human_tra_model_marginals.txt";

        var valueIndexMap = readParamValueIndexMap(new FileReader(paramsPath));
        System.out.println(readProbabilityMap(new FileReader(marginalsPath), valueIndexMap));
    }

    Map<String, Map<Integer, String>> readParamValueIndexMap(Reader r)
            throws IOException {
        // Initialize parameter value index map
        var paramValueIndexMap = new HashMap<String, Map<Integer, String>>();
        for (String name : allowedParams) {
            paramValueIndexMap.put(name, new HashMap<>());
        }

        // Fill it from file
        try (BufferedReader br = new BufferedReader(r)) {
            String paramName = null;

            for (String line; (line = br.readLine()) != null; ) {
                if (line.startsWith("#")) {
                    // parse param name
                    var splitString = line.substring(1).split(";");
                    paramName = splitString[splitString.length - 1].trim();

                    // check if we need this param
                    if (!allowedParams.contains(paramName)) {
                        paramName = null;
                    }
                } else if (paramName != null) {
                    if (line.startsWith("%")) {
                        // value index entry
                        var splitString = line.substring(1).split(";");
                        String paramValue = splitString[0].trim();
                        int paramIndex = Integer.parseInt(splitString[splitString.length - 1].trim());
                        paramValueIndexMap.get(paramName).put(paramIndex, paramValue);
                    } else {
                        // no value and no parameter name in this entry - spurious part so wait for new param name/etc
                        paramName = null;
                    }
                }
            }
        }

        return paramValueIndexMap;
    }

    Map<String, Map<String, Double>> readProbabilityMap(Reader r,
                                                        Map<String, Map<Integer, String>> paramValueIndexMap)
            throws IOException {
        // initialize probabilities & check that we have all param values
        var probabilityMap = new HashMap<String, Map<String, Double>>();
        for (String name : allowedProbabilities) {
            probabilityMap.put(name, new HashMap<>());

            for (String p : name.split("\\|")) {
                if (!p.equals("")) {
                    if (!paramValueIndexMap.containsKey(p)) {
                        throw new IllegalArgumentException(
                                "Parameter value index map doesn't contain required parameter:" + p
                        );
                    }
                }
            }
        }

        // Fill it from file
        try (BufferedReader br = new BufferedReader(r)) {
            String firstParamName = null;
            String probabilityName = null;
            String[] otherParams = null;
            Map<String, Integer> otherParamNameIndex = null;

            for (String line; (line = br.readLine()) != null; ) {
                if (line.startsWith("@")) {
                    // Get full probability name
                    firstParamName = line.substring(1).trim();
                    probabilityName = findProbablityName(firstParamName); // full name - may not match in case of conditional
                    if (probabilityName != null) {
                        otherParams = probabilityName.contains("|") ? // conditional?
                                probabilityName.split("\\|")[1].split(",") : null;
                    }
                } else if (probabilityName != null) {
                    if (line.startsWith("#") && otherParams != null) {
                        // value index entry
                        var splitString = line.substring(1).split("],?");
                        otherParamNameIndex = new HashMap<>();
                        for (String token : splitString) {
                            if (!token.isEmpty()) {
                                var splitString2 = token.split(",");
                                otherParamNameIndex.put(splitString2[0].substring(1).trim(),
                                        Integer.parseInt(splitString2[1].trim()));
                            }
                        }
                    } else if (line.startsWith("%")) {
                        String variableSuffix = "";
                        if (otherParams != null) {
                            // check other params in proper order specified by probability naming
                            for (int i = 0; i < otherParams.length; i++) {
                                String otherParamName = otherParams[i];
                                int index = otherParamNameIndex.get(otherParamName);
                                String value = paramValueIndexMap.get(otherParamName).get(index);

                                if (variableSuffix.isEmpty()) {
                                    variableSuffix += "|" + value;
                                } else {
                                    variableSuffix += "," + value;
                                }
                            }
                        }

                        var probabilities = Arrays.stream(line.substring(1).split(","))
                                .mapToDouble(Double::parseDouble).toArray();
                        for (int i = 0; i < probabilities.length; i++) {
                            String variable = paramValueIndexMap.get(firstParamName).get(i) + variableSuffix;
                            probabilityMap.get(probabilityName).put(variable, probabilities[i]);
                        }
                    }
                }
            }
        }

        return probabilityMap;
    }

    String findProbablityName(String prefix) {
        for (String name : allowedProbabilities) {
            if (name.startsWith(prefix)) {
                return name;
            }
        }
        return null;
    }

    Set<String> allowedParams = new HashSet<>(Arrays.asList("v_choice",
            "j_choice", "v_3_del",
            "j_5_del",
            "vj_ins"//, "vj_dinucl"
    )
    );

    Set<String> allowedProbabilities = new HashSet<>(Arrays.asList(
            "v_choice",
            "j_choice|v_choice", "v_3_del|v_choice",
            "j_5_del|j_choice",
            "vj_ins"//, "vj_dinucl"
    )
    );
}
