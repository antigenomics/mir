package com.milaboratory.mir.graph;

import com.milaboratory.mir.clonotype.Clonotype;

public class Edge {
    private final Clonotype from, to;

    public Edge(Clonotype from, Clonotype to) {
        this.from = from;
        this.to = to;
    }

    public Clonotype getFrom() {
        return from;
    }

    public Clonotype getTo() {
        return to;
    }
}
