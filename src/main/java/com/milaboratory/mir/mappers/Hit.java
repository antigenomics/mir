package com.milaboratory.mir.mappers;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

public class Hit<Q, T, S extends Sequence<S>> {
    private final Q query;
    private final T target;
    private final double alignmentScore;
    private final Mutations<S> mutations;

    public Hit(Q query, T target,
               double alignmentScore,
               Mutations<S> mutations) {
        this.query = query;
        this.target = target;
        this.alignmentScore = alignmentScore;
        this.mutations = mutations;
    }

    public Q getQuery() {
        return query;
    }

    public T getTarget() {
        return target;
    }

    public double getAlignmentScore() {
        return alignmentScore;
    }

    public Mutations<S> getMutations() {
        return mutations;
    }
}
