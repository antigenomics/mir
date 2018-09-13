package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitWithAlignment;

public final class StmHit<Q, T, S extends Sequence<S>> implements HitWithAlignment<Q, T, S> {
    private final Q query;
    private final T target;
    private final float alignmentScore;
    private final S targetSequence;
    private final Mutations<S> mutations;

    public StmHit(Q query, T target, S targetSequence,
                  float alignmentScore, Mutations<S> mutations) {
        this.query = query;
        this.target = target;
        this.targetSequence = targetSequence;
        this.alignmentScore = alignmentScore;
        this.mutations = mutations;
    }

    public Alignment<S> getAlignment() {
        return new Alignment<>(
                targetSequence, mutations, alignmentScore
        );
    }

    @Override
    public Q getQuery() {
        return query;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public float getScore() {
        return alignmentScore;
    }
}
