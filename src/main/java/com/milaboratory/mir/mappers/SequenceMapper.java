package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

public interface SequenceMapper<Q, T, S extends Sequence<S>, H extends HitWithAlignment<Q, T, S>>
        extends Mapper<Q, T, H> {
    SequenceProvider<T, S> getTargetSequenceProvider();

    SequenceProvider<Q, S> getQuerySequenceProvider();
}