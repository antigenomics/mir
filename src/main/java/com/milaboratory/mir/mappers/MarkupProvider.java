package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;

@FunctionalInterface
public interface MarkupProvider<T, S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>> {
    M getMarkup(T obj);

    default SequenceProvider<T, S> asSequenceProvider() {
        return x -> getMarkup(x).getFullSequence();
    }
}
