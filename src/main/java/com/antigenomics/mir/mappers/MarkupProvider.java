package com.antigenomics.mir.mappers;

import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.core.sequence.Sequence;

@FunctionalInterface
public interface MarkupProvider<T, S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>> {
    M getMarkup(T obj);

    default SequenceProvider<T, S> asSequenceProvider() {
        return x -> getMarkup(x).getFullSequence();
    }
}
