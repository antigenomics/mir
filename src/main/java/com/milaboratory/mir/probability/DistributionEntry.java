package com.milaboratory.mir.probability;

import java.io.Serializable;
import java.util.Objects;

public class DistributionEntry<T>
        implements Comparable<DistributionEntry<T>>,
        Serializable {
    private final T value;
    private final double probability;

    public DistributionEntry(T value, double probability) {
        this.value = value;
        this.probability = probability;
    }

    public T getValue() {
        return value;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public int compareTo(DistributionEntry<T> o) {
        return -Double.compare(probability, o.probability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionEntry<?> that = (DistributionEntry<?>) o;
        return Double.compare(that.probability, probability) == 0 &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, probability);
    }
}
