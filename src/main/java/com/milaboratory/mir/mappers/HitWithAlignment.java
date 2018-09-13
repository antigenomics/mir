package com.milaboratory.mir.mappers;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public interface HitWithAlignment<Q, T, S extends Sequence<S>> extends Hit<Q, T> {
    Alignment<S> getAlignment();
}
