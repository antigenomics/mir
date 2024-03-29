package com.antigenomics.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

@FunctionalInterface
public interface SequenceProvider<T, S extends Sequence<S>> {
    S getSequence(T obj);
}
