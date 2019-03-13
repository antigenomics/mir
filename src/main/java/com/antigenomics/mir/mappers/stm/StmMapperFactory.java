package com.antigenomics.mir.mappers.stm;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.SequenceProvider;

public final class StmMapperFactory<S extends Sequence<S>> implements SequenceMapperFactory<S> {
    private final Alphabet<S> alphabet;
    private final SequenceSearchScope searchScope;
    private final ExplicitAlignmentScoring<S> scoring;

    public StmMapperFactory(Alphabet<S> alphabet,
                            SequenceSearchScope searchScope,
                            ExplicitAlignmentScoring<S> scoring) {
        this.alphabet = alphabet;
        this.searchScope = searchScope;
        this.scoring = scoring;
    }

    @Override
    public <T> SequenceMapper<T, S> create(Iterable<T> targets, SequenceProvider<T, S> sequenceProvider) {
        return new StmMapper<>(targets, sequenceProvider, alphabet, searchScope, scoring);
    }
}
