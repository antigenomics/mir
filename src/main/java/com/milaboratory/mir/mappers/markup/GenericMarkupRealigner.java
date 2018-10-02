package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;

import java.util.Optional;

public class GenericMarkupRealigner<T, S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>>
        implements MarkupRealigner<S, E, M> {
    private final SequenceMapper<T, S> mapper;
    private final MarkupProvider<T, S, E, M> markupProvider;

    public GenericMarkupRealigner(SequenceMapper<T, S> mapper,
                                  MarkupProvider<T, S, E, M> markupProvider) {
        this.mapper = mapper;
        this.markupProvider = markupProvider;
    }

    public Optional<M> recomputeMarkup(S query) {
        return mapper
                .map(query)
                .getBestHit()
                .map(
                        hit -> markupProvider
                                .getMarkup(hit.getTarget())
                                .realign(query, hit.getAlignment())
                );
    }

    public SequenceMapper<T, S> getMapper() {
        return mapper;
    }

    public MarkupProvider<T, S, E, M> getMarkupProvider() {
        return markupProvider;
    }
}
