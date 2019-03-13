package com.antigenomics.mir.probability.parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class PlainTextModelIO {
    public static final String HEADER = "variables\tvalues\tprobability";

    public static PlainTextHierarchicalModel readModel(InputStream inputStream) throws IOException {
        Map<String, Map<String, Double>> probabilities = new HashMap<>();

        boolean firstLine = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (firstLine && line.equals(HEADER)) {
                    firstLine = false;
                    continue;
                }

                String[] splitLine = line.split("\t");
                probabilities.computeIfAbsent(splitLine[0], x -> new HashMap<>())
                        .put(splitLine[1], Double.parseDouble(splitLine[2]));
            }
        }

        return new PlainTextHierarchicalModel(probabilities);
    }

    public static void writeModel(PlainTextHierarchicalModel model, OutputStream outputStream) {
        try (PrintWriter printWriter = new PrintWriter(outputStream)) {
            printWriter.println(HEADER);
            model.getProbabilities().forEach((k1, vv) ->
                    vv.forEach((k2, v2) ->
                            printWriter.println(k1 + "\t" + k2 + "\t" + v2))
            );
        }
    }
}
