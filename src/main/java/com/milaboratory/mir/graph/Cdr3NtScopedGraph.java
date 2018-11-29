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

public class Cdr3NtScopedGraph<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> {
    private final StmMapper<T, AminoAcidSequence> stmMapper;
    private final SequenceSearchScope ntSearchScope, aaSearchScope;
    private final boolean requireSegmentMatch;
    private final int k;

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             SequenceSearchScope ntSearchScope,
                             SequenceSearchScope aaSearchScope,
                             boolean requireSegmentMatch) {
        this.stmMapper = new StmMapper<>(
                clonotypes.stream(),
                Clonotype::getCdr3Aa,
                AminoAcidSequence.ALPHABET,
                aaSearchScope,
                DummyExplicitAlignmentScoring.instance()
        );
        this.ntSearchScope = ntSearchScope;
        this.aaSearchScope = aaSearchScope;
        this.requireSegmentMatch = requireSegmentMatch;
        this.k = k;
    }

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             int maxSubstitutions, int maxIndels,
                             int maxAaSubstitutions, int maxAaIndels,
                             boolean requireSegmentMatch) {
        this(clonotypes, k,
                getSearchScope(maxSubstitutions, maxIndels),
                getSearchScope(
                        calcOrGetAaCount(maxSubstitutions, maxAaSubstitutions),
                        calcOrGetAaCount(maxIndels, maxAaIndels)),
                requireSegmentMatch
        );
    }

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             int maxSubstitutions, int maxIndels,
                             int maxAaSubstitutions, int maxAaIndels) {
        this(clonotypes, k, maxSubstitutions, maxIndels, maxAaSubstitutions, maxAaIndels, true);
    }

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             int maxSubstitutions, int maxIndels) {
        this(clonotypes, k, maxSubstitutions, maxIndels, calcAaCount(maxSubstitutions), calcAaCount(maxIndels));
    }

    private static SequenceSearchScope getSearchScope(int maxSubstitutions, int maxIndels) {
        return new SequenceSearchScope(maxSubstitutions,
                maxIndels,
                maxSubstitutions + maxIndels,
                false,
                true);
    }

    private static int calcOrGetAaCount(int ntCount, int aaCount) {
        return aaCount < 0 ? calcAaCount(ntCount) : aaCount;
    }

    private static int calcAaCount(int ntCount) {
        return ntCount / 3 + (ntCount % 3 == 0 ? 0 : 1);
    }

    private Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> flatMap(Stream<T> stream) {
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
                                            return new ClonotypeEdgeWithCdr3Alignment<>(
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

    private <S extends Segment> boolean segmentsMatch(List<SegmentCall<S>> segmentCalls1,
                                                      List<SegmentCall<S>> segmentCalls2) {
        for (SegmentCall<S> s1 : segmentCalls1) {
            for (SegmentCall<S> s2 : segmentCalls2) {
                if (s1.getSegment().getId().equals(s2.getSegment().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean passesFilter(ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence> edge) {
        return (!requireSegmentMatch ||
                (segmentsMatch(edge.getFrom().getVariableSegmentCalls(), edge.getTo().getVariableSegmentCalls()) &&
                        segmentsMatch(edge.getFrom().getJoiningSegmentCalls(), edge.getTo().getJoiningSegmentCalls()))) &&
                goodAlignment(edge.getAlignment());
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> stream() {
        return flatMap(stmMapper.stream());
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> parallelStream() {
        return flatMap(stmMapper.parallelStream());
    }

    public SequenceSearchScope getNtSearchScope() {
        return ntSearchScope;
    }

    public SequenceSearchScope getAaSearchScope() {
        return aaSearchScope;
    }

    public boolean isRequireSegmentMatch() {
        return requireSegmentMatch;
    }

    public int getK() {
        return k;
    }
}