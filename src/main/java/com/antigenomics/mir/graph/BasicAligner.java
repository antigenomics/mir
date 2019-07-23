package com.antigenomics.mir.graph;

import com.antigenomics.mir.mappers.SequenceProvider;
import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.Sequence;

import java.util.function.BiFunction;

public class BasicAligner<T, S extends Sequence<S>> implements BiFunction<T, T, Alignment<S>> {
    private final AlignmentScoring<S> alignmentScoring;
    private final SequenceProvider<T, S> sequenceProvider;
    private final boolean localAlignment;

    public BasicAligner(AlignmentScoring<S> alignmentScoring,
                        SequenceProvider<T, S> sequenceProvider,
                        boolean localAlignment) {
        this.alignmentScoring = alignmentScoring;
        this.sequenceProvider = sequenceProvider;
        this.localAlignment = localAlignment;
    }

    public Alignment<S> apply(T obj1, T obj2) {
        S seq1 = sequenceProvider.getSequence(obj1), seq2 = sequenceProvider.getSequence(obj2);
        return localAlignment ? Aligner.alignLocal(alignmentScoring, seq1, seq2) :
                Aligner.alignGlobal(alignmentScoring, seq1, seq2);
    }

    public SequenceProvider<T, S> getSequenceProvider() {
        return sequenceProvider;
    }

    public boolean isLocalAlignment() {
        return localAlignment;
    }

    public AlignmentScoring<S> getAlignmentScoring() {
        return alignmentScoring;
    }
}
