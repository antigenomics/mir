package com.milaboratory.mir.mappers.align;

import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.SequenceMapper;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.SequenceProvider;

public final class SimpleExhaustiveMapperFactory< S extends Sequence<S>> implements SequenceMapperFactory< S> {
    private final AlignmentScoring<S> alignmentScoring;

    public SimpleExhaustiveMapperFactory(AlignmentScoring<S> alignmentScoring) {
        this.alignmentScoring = alignmentScoring;
    }

    @Override
    public <T> SequenceMapper<T, S> create(Iterable<T> targets, SequenceProvider<T, S> sequenceProvider) {
        return new SimpleExhaustiveMapper<>(targets, sequenceProvider, alignmentScoring);
    }
}
