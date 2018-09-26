package com.milaboratory.mir.probability;

import java.util.*;
import java.util.stream.Collectors;

// immutable
public class DistributionMap<T> {
    public static <T> DistributionMap<T> getDummy() {
        return new DistributionMap<>(new HashMap<>(), true);
    }

    private final Map<T, Double> probabilities;

    static <T> DistributionMap<T> fromValueSet(Set<T> valueSet, double p) {
        return new DistributionMap<>(
                valueSet.stream().collect(Collectors.toMap(
                        x -> x,
                        x -> p
                ))
        );
    }

    public static <T> DistributionMap<T> uniformFromValues(Set<T> valueSet) {
        return fromValueSet(valueSet, 1d / valueSet.size());
    }

    public static <T> DistributionMap<T> zeroFromValues(Set<T> valueSet) {
        return fromValueSet(valueSet, 0d);
    }

    private static Set<Integer> intValues(int lower, int upper) {
        var res = new HashSet<Integer>();
        for (int i = lower; i < upper; i++) {
            res.add(i);
        }
        return res;
    }

    public static DistributionMap<Integer> uniformForIntRange(int lower, int upper) {
        return uniformFromValues(intValues(lower, upper));
    }

    public static DistributionMap<Integer> zeroFromValues(int lower, int upper) {
        return zeroFromValues(intValues(lower, upper));
    }

    public DistributionMap(Map<T, Double> probabilities) {
        this(probabilities, false);
    }

    DistributionMap(Map<T, Double> probabilities, boolean unsafe) {
        if (unsafe) {
            // without cloning
            this.probabilities = probabilities;
        } else {
            if (probabilities.isEmpty()) {
                throw new IllegalArgumentException("No entries provided");
            }
            this.probabilities = new HashMap<>(probabilities);
        }
    }

    /*public DistributionMap(List<DistributionEntry<T>> entryList) {
        if (entryList.isEmpty()) {
            throw new IllegalArgumentException("No entries provided");
        }
        this.probabilities = new HashMap<>();
        entryList.forEach(x -> probabilities.put(x.getValue(), x.getProbability()));
    }*/

    public List<DistributionEntry<T>> listEntries() {
        return probabilities.entrySet().stream()
                .map(x -> new DistributionEntry<>(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    public Set<T> values() {
        return Collections.unmodifiableSet(probabilities.keySet());
    }

    public double getProbability(T value) {
        return probabilities.getOrDefault(value, 0d);
    }

    public double getProbabilityStrict(T value) {
        var p = probabilities.get(value);
        if (p == null) {
            throw new IllegalArgumentException("Value " + value.toString() + " not present in map.");
        }
        return p;
    }

    public boolean isDummy() {
        return probabilities.isEmpty();
    }
}
