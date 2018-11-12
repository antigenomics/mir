package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

public final class BinnedClonotype<T extends Clonotype, K extends ClonotypeKey> {
    private final K clonotypeKey;
    private final double weight;
    private final T clonotype;

    public BinnedClonotype(K clonotypeKey, double weight, T clonotype) {
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

    public BinnedClonotype<T, K> scaleWeight(double scalar) {
        return new BinnedClonotype<>(clonotypeKey, weight * scalar, clonotype);
    }
}
