package com.antigenomics.mir.graph;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.pipe.Pipe;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.AminoAcidSequence;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Cdr3VJPairwiseDistances<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithFullAlignment<T, AminoAcidSequence>> {
    private final CachedSegmentAligner<AminoAcidSequence> segmentAlignerV, segmentAlignerJ;
    private final Cdr3Aligner<AminoAcidSequence> cdr3Aligner;
    private final Pipe<T> clonotypes1, clonotypes2;

    public Cdr3VJPairwiseDistances(Pipe<T> clonotypes1,
                                   Pipe<T> clonotypes2,
                                   AlignmentScoring<AminoAcidSequence> scoringCdr3,
                                   AlignmentScoring<AminoAcidSequence> scoringVJ) {
        this.clonotypes1 = clonotypes1;
        this.clonotypes2 = clonotypes2;
        this.segmentAlignerV = new CachedSegmentAligner<>(scoringVJ);
        this.segmentAlignerJ = new CachedSegmentAligner<>(scoringVJ);
        this.cdr3Aligner = new Cdr3Aligner<>(scoringCdr3);
    }

    @Override
    public Stream<ClonotypeEdgeWithFullAlignment<T, AminoAcidSequence>> stream() {
        return GraphUtils.flatMap(clonotypes1, clonotypes2, this::align, false);
    }

    @Override
    public Stream<ClonotypeEdgeWithFullAlignment<T, AminoAcidSequence>> parallelStream() {
        return GraphUtils.flatMap(clonotypes1, clonotypes2, this::align, true);
    }

    private ClonotypeEdgeWithFullAlignment<T, AminoAcidSequence> align(T clonotype1, T clonotype2) {
        return new ClonotypeEdgeWithFullAlignment<>(clonotype1, clonotype2,
                cdr3Aligner.apply(clonotype1, clonotype2),
                segmentAlignerV.apply(clonotype1.getBestVariableSegment(), clonotype2.getBestVariableSegment()),
                segmentAlignerJ.apply(clonotype1.getBestJoiningSegment(), clonotype2.getBestJoiningSegment()));
    }
}
