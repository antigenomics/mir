package com.milaboratory.mir.graph;


import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.clonotype.Clonotype;

public class EdgeWithAlignment<S extends Sequence<S>> extends Edge {
    private final Alignment<S> alignment;

    public EdgeWithAlignment(Clonotype from, Clonotype to, Alignment<S> alignment) {
        super(from, to);
        this.alignment = alignment;
    }

    public Alignment<S> getAlignment() {
        return alignment;
    }
}
