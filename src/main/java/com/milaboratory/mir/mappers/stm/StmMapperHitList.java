package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitList;

import java.util.List;

public final class StmMapperHitList<Q, T, S extends Sequence<S>>
        extends HitList<StmMapperHit<Q, T, S>> {
    public StmMapperHitList(List<StmMapperHit<Q, T, S>> hits) {
        super(hits);
    }

    StmMapperHitList(List<StmMapperHit<Q, T, S>> hits, boolean unsafe) {
        super(hits, unsafe);
    }
}