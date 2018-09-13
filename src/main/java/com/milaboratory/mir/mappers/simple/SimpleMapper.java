package com.milaboratory.mir.mappers.simple;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitWithAlignmentImpl;

public interface SimpleMapper<Q, T, S extends Sequence<S>> {
    HitWithAlignmentImpl<Q, T, S> map(Q query);
}
