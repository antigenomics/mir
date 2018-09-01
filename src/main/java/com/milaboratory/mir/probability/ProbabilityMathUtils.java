package com.milaboratory.mir.probability;


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
        assertNormalized(values, false, 1e-6);
    }

    public static void assertNormalized(Collection<Double> values,
                                        boolean allowZeroMeasure,
                                        double precision) {
        if (!isNormalized(values, allowZeroMeasure, precision)) {
            String res = values.stream().map(Object::toString).collect(Collectors.joining(","));
            throw new IllegalArgumentException("Non-normalized probability distribution [" + res +
                    "] allowZeroMeasure=" + allowZeroMeasure + " precision=" + precision);
        }
    }

    public static double sum(Collection<Double> values) {
        double sum = 0;

        for (double value : values) {
            sum += value;
        }

        return sum;
    }
}
