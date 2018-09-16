package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;

public interface SequenceMapper<T, S extends Sequence<S>>
        extends Mapper<S, T, HitWithAlignment<T, S>> {
    SequenceProvider<T, S> getSequenceProvider();

    Alphabet<S> getAlphabet();
}