package com.milaboratory.mir.graph;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.MutationType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.mappers.stm.DummyExplicitAlignmentScoring;
import com.milaboratory.mir.mappers.stm.SequenceSearchScope;
import com.milaboratory.mir.mappers.stm.StmMapper;
import com.milaboratory.mir.pipe.Pipe;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentCall;

import java.util.List;
import java.util.stream.Stream;

public class IgCdr3NtScopedGraph<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> {
    private final StmMapper<T, AminoAcidSequence> stmMapper;
    private final SequenceSearchScope ntSearchScope;
    private final boolean requireSegmentMatch;
    private final int k;

    public IgCdr3NtScopedGraph(Pipe<T> clonotypes,
                               int k,
                               SequenceSearchScope ntSearchScope,
                               boolean requireSegmentMatch,
                               SequenceSearchScope aaSearchScope) {
        this.stmMapper = new StmMapper<>(
                clonotypes.stream(),
                Clonotype::getCdr3Aa,
                AminoAcidSequence.ALPHABET,
                aaSearchScope,
                DummyExplicitAlignmentScoring.instance()
        );
        this.ntSearchScope = ntSearchScope;
        this.requireSegmentMatch = requireSegmentMatch;
        this.k = k;
    }

    public IgCdr3NtScopedGraph(Pipe<T> clonotypes,
                               int k,
                               SequenceSearchScope ntSearchScope,
                               boolean requireSegmentMatch) {
        this(clonotypes, k, ntSearchScope, requireSegmentMatch,
                new SequenceSearchScope(ntSearchScope.getMaxSubstitutions() / 3,
                        ntSearchScope.getMaxDeletions() / 3,
                        ntSearchScope.getMaxInsertions() / 3,
                        ntSearchScope.getMaxTotal() / 3,
                        ntSearchScope.isExhaustive(),
                        ntSearchScope.isGreedy()));
    }

    public IgCdr3NtScopedGraph(Pipe<T> clonotypes,
                               int k,
                               int maxSubstitutions, int maxIndels, int maxTotal,
                               boolean requireSegmentMatch) {
        this(clonotypes, k, new
                        SequenceSearchScope(maxSubstitutions, maxIndels, maxTotal, false, true),
                requireSegmentMatch);
    }

    public IgCdr3NtScopedGraph(Pipe<T> clonotypes,
                               int k,
                               int maxSubstitutions, int maxIndels, int maxTotal) {
        this(clonotypes, k, maxSubstitutions, maxIndels, maxTotal, true);
    }

    private Stream<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> flatMap(Stream<T> stream) {
        return stream
                .flatMap(from ->
                        stmMapper
                                .map(from.getCdr3Aa())
                                .getHits()
                                .stream()
                                .filter(x -> x.getTarget() != from)
                                .limit(k)
                                .map(hit -> {
                                            var to = hit.getTarget();
                                            return new ClonotypeEdgeWithAlignment<>(
                                                    from, to,
                                                    getAlignment(from, to)
                                            );
                                        }
                                )
                                .filter(this::passesFilter));
    }

    private Alignment<NucleotideSequence> getAlignment(T query, T target) {
        return Aligner.alignGlobalAffine(AffineGapAlignmentScoring.getNucleotideBLASTScoring(),
                query.getCdr3Nt(),
                target.getCdr3Nt());
    }

    private boolean goodAlignment(Alignment<NucleotideSequence> alignment) {
        int total = alignment.getAbsoluteMutations().size(),
                substitutions = alignment.getAbsoluteMutations().countOf(MutationType.Substitution),
                indels = total - substitutions;

        return substitutions <= ntSearchScope.getMaxSubstitutions() &&
                indels <= ntSearchScope.getMaxIndels() &&
                total <= ntSearchScope.getMaxTotal();
    }

    private <T extends Segment> boolean segmentsMatch(List<SegmentCall<T>> segmentCalls1,
                                                      List<SegmentCall<T>> segmentCalls2) {
        for (SegmentCall<T> s1 : segmentCalls1) {
            for (SegmentCall<T> s2 : segmentCalls2) {
                if (s1.getSegment().getId().equals(s2.getSegment().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean passesFilter(ClonotypeEdgeWithAlignment<T, NucleotideSequence> edge) {
        return (!requireSegmentMatch ||
                (segmentsMatch(edge.getFrom().getVariableSegmentCalls(), edge.getTo().getVariableSegmentCalls()) &&
                        segmentsMatch(edge.getFrom().getJoiningSegmentCalls(), edge.getTo().getJoiningSegmentCalls()))) &&
                goodAlignment(edge.getAlignment());
    }

    @Override
    public Stream<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> stream() {
        return flatMap(stmMapper.stream());

    }

    @Override
    public Stream<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> parallelStream() {
        return flatMap(stmMapper.parallelStream());
    }
}