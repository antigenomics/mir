package com.antigenomics.mir.graph;


import com.antigenomics.mir.clonotype.Clonotype;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public final class ClonotypeEdgeWithFullAlignment<T extends Clonotype, S extends Sequence<S>> extends
        ClonotypeEdgeWithCdr3Alignment<T, S> {
    private final Alignment<S> vAlignment, jAlignment;

    public ClonotypeEdgeWithFullAlignment(T from, T to, Alignment<S> cdr3Alignment,
                                          Alignment<S> vAlignment, Alignment<S> jAlignment) {
        super(from, to, cdr3Alignment);
        this.vAlignment = vAlignment;
        this.jAlignment = jAlignment;
    }

    public Alignment<S> getvAlignment() {
        return vAlignment;
    }

    public Alignment<S> getjAlignment() {
        return jAlignment;
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + vAlignment.getScore() + "\t" + jAlignment.getScore();
    }
}
