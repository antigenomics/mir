package com.antigenomics.mir.mappers.align;

import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Sequence;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.SequenceProvider;

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
