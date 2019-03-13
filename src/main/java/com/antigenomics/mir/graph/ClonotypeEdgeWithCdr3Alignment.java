package com.antigenomics.mir.graph;


import com.antigenomics.mir.clonotype.Clonotype;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public class ClonotypeEdgeWithCdr3Alignment<T extends Clonotype, S extends Sequence<S>> extends ClonotypeEdge<T> {
    private final Alignment<S> alignment;

    public ClonotypeEdgeWithCdr3Alignment(T from, T to, Alignment<S> alignment) {
        super(from, to);
        this.alignment = alignment;
    }

    public Alignment<S> getAlignment() {
        return alignment;
    }

    @Override
    public String toString() {
        var helper = alignment.getAlignmentHelper();
        return super.toString() + "\t" + helper.getSeq1String() + "\t" + helper.getSeq2String();
    }
}
