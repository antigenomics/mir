package com.antigenomics.mir.mappers.stm;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;
import com.antigenomics.mir.mappers.HitWithAlignment;

public final class StmMapperHit<T, S extends Sequence<S>> implements HitWithAlignment<T, S> {
    private final T target;
    private final float alignmentScore;
    private final S querySequence;
    private final Mutations<S> mutations;

    public StmMapperHit(T target, S querySequence,
                        float alignmentScore, Mutations<S> mutations) {
        this.target = target;
        this.querySequence = querySequence;
        this.alignmentScore = alignmentScore;
        this.mutations = mutations;
    }

    public Alignment<S> getAlignment() {
        return new Alignment<>(
                querySequence, mutations, alignmentScore
        );
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
