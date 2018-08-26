package com.milaboratory.mir.probability;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class ProbabilityMathUtils {
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

    /**
     * Roulette wheel sampling. Can be sped up by sorting probabilities in a descending order.
     * The sampling is performed using thread local random generator. Sort the array in
     * descending order for speedup.
     *
     * @param probabilities an array of event probabilities, should sum to 1
     * @return index of sampled event
     */
    public static int sample(double[] probabilities) {
        double p = ThreadLocalRandom.current().nextDouble();
        double sum = 0;

        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (sum >= p) {
                return i;
            }
        }

        return probabilities.length - 1;
    }
}
