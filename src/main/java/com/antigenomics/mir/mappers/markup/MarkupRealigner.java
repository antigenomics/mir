package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.Optional;

public interface MarkupRealigner<S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>> {
    Optional<MarkupRealignmentResult<S, E, M>> recomputeMarkup(S query);
}
