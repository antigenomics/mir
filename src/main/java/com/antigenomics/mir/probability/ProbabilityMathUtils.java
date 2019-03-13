package com.antigenomics.mir.probability;


import java.util.Collection;
import java.util.stream.Collectors;

public final class ProbabilityMathUtils {
    public static boolean isNormalized(Collection<Double> values,
                                       boolean allowZeroMeasure,
                                       double precision) {
        double sum = sum(values);
        return Math.abs(sum - 1.0) <= precision || (allowZeroMeasure && Math.abs(sum) <= precision);
    }

    public static void assertNormalized(Collection<Double> values) {
        assertNormalized(values, false, 1e-5);
    }

    public static void assertNormalized(Collection<Double> values,
                                        boolean allowZeroMeasure,
                                        double precision) {
        if (!isNormalized(values, allowZeroMeasure, precision)) {
            String res = values.stream().limit(10).map(Object::toString).collect(Collectors.joining(","));
            throw new IllegalArgumentException("Non-normalized probability distribution [" + res +
                    "... ] sum=" + sum(values) + " allowZeroMeasure=" + allowZeroMeasure + " precision=" + precision);
        }
    }

    public static double sum(Collection<Double> values) {
        double sum = 0;

        for (double value : values) {
            sum += value;
        }

        return sum;
    }

    public static boolean isNormalized(double[] arr, double precision) {
        double sum = sum(arr);
        return Math.abs(sum) <= precision || Math.abs(sum - 1) <= precision;
    }

    public static boolean isNormalized(double[] arr) {
        return isNormalized(arr, 1e-5);
    }

    public static double sum(double[] arr) {
        double sum = 0;
        for (double x : arr) {
            sum += x;
        }
        return sum;
    }

    public static double sum(double[][] arr2) {
        double sum = 0;
        for (double[] row : arr2) {
            for (double x : row) {
                sum += x;
            }
        }
        return sum;
    }

    public static double[] normalize(double[] arr) {
        return normalize(arr, sum(arr));
    }

    private static double[] normalize(double[] arr, double value) {
        double[] res = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i] / value;
        }
        return res;
    }

    public static double[] toMarginal(double[][] jointProbability) {
        double[] marg = new double[jointProbability.length];
        for (int i = 0; i < jointProbability.length; i++) {
            marg[i] = sum(jointProbability[i]);
        }
        return marg;
    }

    public static double[][] toConditional(double[][] jointProbability, double[] marginal) {
        double[][] res = new double[jointProbability.length][];
        for (int i = 0; i < jointProbability.length; i++) {
            res[i] = normalize(jointProbability[i], marginal[i]);
        }
        return res;
    }

    public static double[][] toConditional(double[][] jointProbability) {
        return toConditional(jointProbability, toMarginal(jointProbability));
    }

}
