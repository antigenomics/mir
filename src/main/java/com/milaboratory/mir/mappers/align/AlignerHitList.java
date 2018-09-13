package com.milaboratory.mir.mappers.align;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitList;
import com.milaboratory.mir.mappers.HitWithAlignmentImpl;

import java.util.List;

public final class AlignerHitList<Q, T, S extends Sequence<S>>
        extends HitList<HitWithAlignmentImpl<Q, T, S>> {
    public AlignerHitList(List<HitWithAlignmentImpl<Q, T, S>> hits) {
        super(hits);
    }

    AlignerHitList(List<HitWithAlignmentImpl<Q, T, S>> hits, boolean unsafe) {
        super(hits, unsafe);
    }
}
