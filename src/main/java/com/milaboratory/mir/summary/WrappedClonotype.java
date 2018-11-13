package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

public final class WrappedClonotype<T extends Clonotype, K extends ClonotypeGroup> {
    private final K clonotypeKey;
    private final double weight;
    private final T clonotype;

    public WrappedClonotype(K clonotypeKey, double weight, T clonotype) {
        this.clonotypeKey = clonotypeKey;
        this.weight = weight;
        this.clonotype = clonotype;
    }

    public K getClonotypeKey() {
        return clonotypeKey;
    }

    public double getWeight() {
        return weight;
    }

    public T getClonotype() {
        return clonotype;
    }

    public WrappedClonotype<T, K> scaleWeight(double scalar) {
        return new WrappedClonotype<>(clonotypeKey, weight * scalar, clonotype);
    }
}
