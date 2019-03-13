package com.antigenomics.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

public final class DummyExplicitAlignmentScoring<S extends Sequence<S>> implements ExplicitAlignmentScoring<S> {
    public static final <S extends Sequence<S>> DummyExplicitAlignmentScoring<S> instance() {
        return new DummyExplicitAlignmentScoring<>();
    }

    private DummyExplicitAlignmentScoring() {

    }

    @Override
    public float computeScore(S query, Mutations<S> mutations) {
        return -mutations.size();
    }
}