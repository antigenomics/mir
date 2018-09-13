package com.milaboratory.mir.mappers.simple;

import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.HitWithAlignmentImpl;
import com.milaboratory.mir.mappers.ObjectWithSequence;
import com.milaboratory.mir.mappers.SequenceProvider;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleExhaustiveMapper<Q, T, S extends Sequence<S>> implements SimpleMapper<Q, T, S> {
    private final List<ObjectWithSequence<T, S>> targets;
    private final SequenceProvider<Q, S> querySequenceProvider;
    private final AlignmentScoring<S> alignmentScoring;

    public SimpleExhaustiveMapper(List<T> targets,
                                  SequenceProvider<T, S> targetSequenceProvider,
                                  SequenceProvider<Q, S> querySequenceProvider,
                                  AlignmentScoring<S> alignmentScoring) {
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Empty set of target objects provided.");
        }
        this.targets = targets
                .stream()
                .map(x -> ObjectWithSequence.wrap(x, targetSequenceProvider))
                .collect(Collectors.toList());
        this.querySequenceProvider = querySequenceProvider;
        this.alignmentScoring = alignmentScoring;
    }

    @SuppressWarnings("Optional")
    @Override
    public HitWithAlignmentImpl<Q, T, S> map(Q query) {
        return targets.stream()
                .map(
                        x -> new HitWithAlignmentImpl<>(query,
                                x.getObject(),
                                Aligner.alignLocal(alignmentScoring,
                                        querySequenceProvider.getSequence(query),
                                        x.getSequence()))
                )
                .max(HitWithAlignmentImpl::compareTo)
                .get();
    }
}