package com.milaboratory.mir.graph;


import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.clonotype.Clonotype;

public class ClonotypeEdgeWithAlignment<T extends Clonotype, S extends Sequence<S>> extends ClonotypeEdge<T> {
    private final Alignment<S> alignment;

    public ClonotypeEdgeWithAlignment(T from, T to, Alignment<S> alignment) {
        super(from, to);
        this.alignment = alignment;
    }

    public Alignment<S> getAlignment() {
        return alignment;
    }
}
