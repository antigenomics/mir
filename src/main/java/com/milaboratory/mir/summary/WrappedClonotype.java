package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.binning.DummyGroup;

public final class WrappedClonotype<T extends Clonotype, G extends ClonotypeGroup> {
    private final G group;
    private final double weight;
    private final T clonotype;

    public static <T extends Clonotype> WrappedClonotype<T, DummyGroup> dummy(T clonotype) {
        return new WrappedClonotype<>(DummyGroup.INSTANCE, 1.0, clonotype);
    }

    public WrappedClonotype(G group, double weight, T clonotype) {
        this.group = group;
        this.weight = weight;
        this.clonotype = clonotype;
    }

    public G getGroup() {
        return group;
    }

    public double getWeight() {
        return weight;
    }

    public T getClonotype() {
        return clonotype;
    }

    public WrappedClonotype<T, G> scaleWeight(double scalar) {
        return new WrappedClonotype<>(group, weight * scalar, clonotype);
    }
}
