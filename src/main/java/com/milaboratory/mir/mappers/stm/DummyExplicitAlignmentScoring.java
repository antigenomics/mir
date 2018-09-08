package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

public final class DummyExplicitAlignmentScoring<S extends Sequence<S>> implements ExplicitAlignmentScoring<S> {
    public static final DummyExplicitAlignmentScoring INSTANCE = new DummyExplicitAlignmentScoring();

    private DummyExplicitAlignmentScoring() {

    }

    @Override
    public double computeScore(S query, Mutations<S> mutations) {
        return -mutations.size();
    }
}