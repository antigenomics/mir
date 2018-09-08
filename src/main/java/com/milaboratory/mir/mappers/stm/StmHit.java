package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.Hit;

public final class StmHit<T, S extends Sequence<S>> extends Hit<T, T, S> {
    public StmHit(T query, T target, double alignmentScore, Mutations<S> mutations) {
        super(query, target, alignmentScore, mutations);
    }
}