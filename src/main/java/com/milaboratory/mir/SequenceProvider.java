package com.milaboratory.mir;

import com.milaboratory.core.sequence.Sequence;

public interface SequenceProvider<T, S extends Sequence<S>> {
    S getSequence(T obj);
}
