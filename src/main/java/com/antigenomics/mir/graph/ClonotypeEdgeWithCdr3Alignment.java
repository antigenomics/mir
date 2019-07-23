package com.antigenomics.mir.graph;


import com.antigenomics.mir.clonotype.Clonotype;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public class ClonotypeEdgeWithCdr3Alignment<T extends Clonotype, S extends Sequence<S>> extends ClonotypeEdge<T> {
    private final Alignment<S> cdr3Alignment;

    public ClonotypeEdgeWithCdr3Alignment(T from, T to, Alignment<S> cdr3Alignment) {
        super(from, to);
        this.cdr3Alignment = cdr3Alignment;
    }

    public Alignment<S> getCdr3Alignment() {
        return cdr3Alignment;
    }

    @Override
    public String toString() {
        var helper = cdr3Alignment.getAlignmentHelper();
        return super.toString() + "\t" + helper.getSeq1String() + "\t" + helper.getSeq2String();
    }
}
