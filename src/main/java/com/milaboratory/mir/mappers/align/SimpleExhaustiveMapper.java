package com.milaboratory.mir.mappers.align;

import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleExhaustiveMapper<Q, T, S extends Sequence<S>>
        implements SequenceMapper<Q, T, S, HitWithAlignmentImpl<Q, T, S>> {
    private final List<ObjectWithSequence<T, S>> targets;
    private final SequenceProvider<Q, S> querySequenceProvider;
    private final SequenceProvider<T, S> targetSequenceProvider;
    private final AlignmentScoring<S> alignmentScoring;

    public SimpleExhaustiveMapper(List<T> targets,
                                  SequenceProvider<T, S> targetSequenceProvider,
                                  SequenceProvider<Q, S> querySequenceProvider,
                                  AlignmentScoring<S> alignmentScoring) {
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Empty set of target objects provided.");
        }
        this.targetSequenceProvider = targetSequenceProvider;
        this.targets = targets
                .stream()
                .map(x -> ObjectWithSequence.wrap(x, targetSequenceProvider))
                .collect(Collectors.toList());
        this.querySequenceProvider = querySequenceProvider;
        this.alignmentScoring = alignmentScoring;
    }

    @SuppressWarnings("Optional")
    @Override
    public AlignerHitList<Q, T, S> map(Q query) {
        return new AlignerHitList<>(targets.stream()
                .map(
                        x -> new HitWithAlignmentImpl<>(query,
                                x.getObject(),
                                Aligner.alignLocal(alignmentScoring,
                                        querySequenceProvider.getSequence(query),
                                        x.getSequence()))
                )
                .collect(Collectors.toList()),
                true);
    }

    public List<ObjectWithSequence<T, S>> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    @Override
    public SequenceProvider<Q, S> getQuerySequenceProvider() {
        return querySequenceProvider;
    }

    @Override
    public SequenceProvider<T, S> getTargetSequenceProvider() {
        return targetSequenceProvider;
    }

    public AlignmentScoring<S> getAlignmentScoring() {
        return alignmentScoring;
    }
}