package com.antigenomics.mir.mappers;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public interface HitWithAlignment<T, S extends Sequence<S>> extends Hit<T> {
    Alignment<S> getAlignment();
}
