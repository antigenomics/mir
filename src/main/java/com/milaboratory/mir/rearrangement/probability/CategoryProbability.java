package com.milaboratory.mir.rearrangement.probability;

public class CategoryProbability<T> {
    private final T category;
    private final double probability;

    public CategoryProbability(T category, double probability) {
        this.category = category;
        this.probability = probability;
    }

    public T getCategory() {
        return category;
    }

    public double getProbability() {
        return probability;
    }
}
