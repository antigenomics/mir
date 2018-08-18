package com.milaboratory.mir.clonotype;

public class ClonotypeCall<T extends Clonotype> {
    private final int id, count;
    private final double frequency;
    private final T clonotype;

    public ClonotypeCall(int id, int count, double frequency, T clonotype) {
        this.id = id;
        this.count = count;
        this.frequency = frequency;
        this.clonotype = clonotype;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public double getFrequency() {
        return frequency;
    }

    public T getClonotype() {
        return clonotype;
    }
}
