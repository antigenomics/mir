package com.milaboratory.mir.mappers.align;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitList;
import com.milaboratory.mir.mappers.HitWithAlignmentImpl;

import java.util.List;

public final class AlignerHitList<T, S extends Sequence<S>>
        extends HitList<HitWithAlignmentImpl<T, S>> {
    public AlignerHitList(List<HitWithAlignmentImpl<T, S>> hits) {
        super(hits);
    }

    AlignerHitList(List<HitWithAlignmentImpl<T, S>> hits, boolean unsafe, boolean sorted) {
        super(hits, unsafe, sorted); // todo: maybe just set sorted to false
    }
}
