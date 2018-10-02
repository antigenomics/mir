package com.milaboratory.mir.graph;

import com.milaboratory.mir.clonotype.Clonotype;

public class ClonotypeEdge<T extends Clonotype> {
    private final T from, to;

    public ClonotypeEdge(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public boolean isRedundant() {
        return from == to;
    }

    @Override
    public String toString() {
        return from + "\t" + to;
    }
}
