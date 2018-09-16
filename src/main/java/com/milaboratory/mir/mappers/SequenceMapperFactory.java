package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

public interface SequenceMapperFactory<S extends Sequence<S>> {
    <T> SequenceMapper<T, S> create(Iterable<T> targets, SequenceProvider<T, S> sequenceProvider);
}
