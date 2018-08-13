package com.milaboratory.mir.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

public class StmHit<T, S extends Sequence<S>> {
    private final T query, target;
    private final double alignmentScore;
    private final Mutations<S> mutations;

    public StmHit(T query, T target,
                  double alignmentScore,
                  Mutations<S> mutations) {
        this.query = query;
        this.target = target;
        this.alignmentScore = alignmentScore;
        this.mutations = mutations;
    }

    public T getQuery() {
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