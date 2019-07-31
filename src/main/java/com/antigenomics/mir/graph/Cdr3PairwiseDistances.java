package com.antigenomics.mir.graph;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.pipe.Pipe;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.AminoAcidSequence;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Cdr3PairwiseDistances<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithCdr3Alignment<T, AminoAcidSequence>> {
    private final Cdr3Aligner<AminoAcidSequence> cdr3Aligner;
    private final Pipe<T> clonotypes1, clonotypes2;

    public Cdr3PairwiseDistances(Pipe<T> clonotypes1,
                                 Pipe<T> clonotypes2,
                                 AlignmentScoring<AminoAcidSequence> scoringCdr3) {
        this.clonotypes1 = clonotypes1;
        this.clonotypes2 = clonotypes2;
        this.cdr3Aligner = new Cdr3Aligner<>(scoringCdr3);
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, AminoAcidSequence>> stream() {
        return GraphUtils.flatMap(clonotypes1, clonotypes2, this::align, false);
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, AminoAcidSequence>> parallelStream() {
        return GraphUtils.flatMap(clonotypes1, clonotypes2, this::align, true);
    }

    private ClonotypeEdgeWithCdr3Alignment<T, AminoAcidSequence> align(T clonotype1, T clonotype2) {
        return new ClonotypeEdgeWithCdr3Alignment<>(clonotype1, clonotype2,
                cdr3Aligner.apply(clonotype1, clonotype2));
    }
}