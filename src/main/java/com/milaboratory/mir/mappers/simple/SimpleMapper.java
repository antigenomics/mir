package com.milaboratory.mir.mappers.simple;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitWithAlignment;

public interface SimpleMapper<Q, T, S extends Sequence<S>> {
    HitWithAlignment<Q, T, S> map(Q query);
}
