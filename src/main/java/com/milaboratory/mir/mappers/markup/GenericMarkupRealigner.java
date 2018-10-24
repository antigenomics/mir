package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;

import java.util.Optional;

public class GenericMarkupRealigner<T, S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>>
        implements MarkupRealigner<S, E, M> {
    private final SequenceMapper<T, S> mapper;
    private final MarkupProvider<T, S, E, M> markupProvider;
    private final double minCoverage;
    private final int minMatches;

    public GenericMarkupRealigner(SequenceMapper<T, S> mapper,
                                  MarkupProvider<T, S, E, M> markupProvider,
                                  double minCoverage, int minMatches) {
        this.mapper = mapper;
        this.markupProvider = markupProvider;
        this.minCoverage = minCoverage;
        this.minMatches = minMatches;
    }

    @Override
    public Optional<MarkupRealignmentResult<S, E, M>> recomputeMarkup(S query) {
        var hitOpt = mapper
                .map(query)
                .getBestHit();

        HitWithAlignment<T, S> hit;
        if (hitOpt.isPresent()) {
            hit = hitOpt.get();
            var alignment = hit.getAlignment();
            int matches = alignment.getSequence2Range().length() - alignment.getAbsoluteMutations().size();
            double coverage = matches / (double) query.size();

            if (matches >= minMatches && coverage >= minCoverage) {
                return Optional.of(new MarkupRealignmentResult<>(
                        markupProvider
                                .getMarkup(hit.getTarget())
                                .realign(query, hit.getAlignment()),
                        matches,
                        coverage,
                        hit.getTarget()
                ));
            }
        }

        return Optional.empty();
    }

    public SequenceMapper<T, S> getMapper() {
        return mapper;
    }

    public MarkupProvider<T, S, E, M> getMarkupProvider() {
        return markupProvider;
    }
}
