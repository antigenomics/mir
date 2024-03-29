package com.antigenomics.mir.summary;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.summary.binning.DummyGroup;

public final class WrappedClonotype<T extends Clonotype, G extends ClonotypeGroup> {
    private final G group;
    private final double weight;
    private final T clonotype;

    public static <T extends Clonotype> WrappedClonotype<T, DummyGroup> dummy(T clonotype) {
        return new WrappedClonotype<>(DummyGroup.INSTANCE, 1.0, clonotype);
    }

    // todo: move weight last param
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

    public WrappedClonotype<T, G> scaleWeight() {
        return scaleWeight(this.clonotype.getWeight());
    }
}
