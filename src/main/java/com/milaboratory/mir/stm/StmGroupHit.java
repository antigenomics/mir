package com.milaboratory.mir.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

import java.util.List;

class StmGroupHit<T, S extends Sequence<S>> {
    private final List<T> group;
    private final double alignmentScore;
    private final Mutations<S> mutations;

    StmGroupHit(List<T> group, double alignmentScore,
                Mutations<S> mutations) {
        this.group = group;
        this.alignmentScore = alignmentScore;
        this.mutations = mutations;
    }

    public List<T> getGroup() {
        return group;
    }

    public double getAlignmentScore() {
        return alignmentScore;
    }

    public Mutations<S> getMutations() {
        return mutations;
    }
}