package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;

import java.util.Optional;

public class MarkupRealigner<Q, T, S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>> {
    private final MarkupProvider<T, S, E, M> markupProvider;
    private final SequenceMapper<Q, T, S, HitWithAlignment<Q, T, S>> mapper;

    public MarkupRealigner(MarkupProvider<T, S, E, M> markupProvider,
                           SequenceMapper<Q, T, S, HitWithAlignment<Q, T, S>> mapper) {
        this.markupProvider = markupProvider;
        this.mapper = mapper;
    }

    public Optional<M> recomputeMarkup(Q query) {
        return mapper
                .map(query)
                .getBestHit()
                .map(
                        hit -> markupProvider
                                .getMarkup(hit.getTarget())
                                .realign(mapper.getQuerySequenceProvider().getSequence(query), hit.getAlignment())
                );
    }
}
