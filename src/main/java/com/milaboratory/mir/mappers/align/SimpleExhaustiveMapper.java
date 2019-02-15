package com.milaboratory.mir.mappers.align;

import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class SimpleExhaustiveMapper<T, S extends Sequence<S>> implements SequenceMapper<T, S> {
    private final List<ObjectWithSequence<T, S>> targets;
    private final SequenceProvider<T, S> sequenceProvider;
    private final AlignmentScoring<S> alignmentScoring;

    public SimpleExhaustiveMapper(Iterable<T> targets,
                                  SequenceProvider<T, S> sequenceProvider,
                                  AlignmentScoring<S> alignmentScoring) {
        this.sequenceProvider = sequenceProvider;
        this.targets = StreamSupport.stream(targets.spliterator(), false)
                .map(x -> ObjectWithSequence.wrap(x, sequenceProvider))
                .collect(Collectors.toList());
        if (this.targets.isEmpty()) {
            throw new IllegalArgumentException("Empty set of target objects provided.");
        }
        this.alignmentScoring = alignmentScoring;
    }

    @SuppressWarnings("Optional")
    @Override
    public AlignerHitList<T, S> map(S query) {
        return new AlignerHitList<>(targets.stream()
                .map(
                        x -> new HitWithAlignmentImpl<>(
                                x.getObject(),
                                Aligner.alignLocal(alignmentScoring,
                                        x.getSequence(), query))
                )
                .collect(Collectors.toList()),
                true, false);
    }

    public List<ObjectWithSequence<T, S>> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    @Override
    public Alphabet<S> getAlphabet() {
        return alignmentScoring.getAlphabet();
    }

    @Override
    public SequenceProvider<T, S> getSequenceProvider() {
        return sequenceProvider;
    }

    public AlignmentScoring<S> getAlignmentScoring() {
        return alignmentScoring;
    }
}