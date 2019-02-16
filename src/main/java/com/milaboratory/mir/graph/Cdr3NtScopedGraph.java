package com.milaboratory.mir.graph;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.mutations.MutationType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.CollectionUtils;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.mappers.stm.DummyExplicitAlignmentScoring;
import com.milaboratory.mir.mappers.stm.SequenceSearchScope;
import com.milaboratory.mir.mappers.stm.StmMapper;
import com.milaboratory.mir.mappers.stm.StmMapperHit;
import com.milaboratory.mir.pipe.Pipe;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentCall;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// NOTE In general this class is intended to be used for TCRs
// requireSegmentMatch to be used for TCRs
// aggregation based on V+J is preferable for BCR lineage analysis
// as it is far faster. You won't expect same B-cell
// lineage to contain distinct V+J
// IMPORTANT this doesn't apply to non-full-length data -- here BCRs and TCRs should
// be treated the same and this class should be used for both
public class Cdr3NtScopedGraph<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> {
    private final Scope2DParameters scope2DParameters;
    private final StmMapper<T, AminoAcidSequence> stmMapper;
    private final boolean requireSegmentMatch;

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             Scope2DParameters scope2DParameters,
                             boolean requireSegmentMatch) {
        this.stmMapper = new StmMapper<>(
                clonotypes.stream(),
                Clonotype::getCdr3Aa,
                AminoAcidSequence.ALPHABET,
                scope2DParameters.getAaSearchScope(),
                DummyExplicitAlignmentScoring.instance()
        );
        this.requireSegmentMatch = requireSegmentMatch;
        this.scope2DParameters = scope2DParameters;
    }

    private Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> flatMap(boolean parallel) {
        // NOTE we return full graph with 1->2 and 2->1 edges
        // this is because there can be asymmetries due to KNN selection
        return (parallel ? stmMapper.parallelStream() : stmMapper.stream())
                .flatMap(from -> {
                    // it is very problematic to return K best hits fairly when
                    // there are ties AND a self-match is present. We do it manually
                    // 1) get hits and remove self-hit
                    var hitList = stmMapper
                            .map(from.getCdr3Aa())
                            .getHits()
                            .stream()
                            .filter(x -> x.getTarget() != from)
                            .collect(Collectors.toList());

                    // 2) select k best hits
                    hitList = CollectionUtils.getKFirst(hitList,
                            scope2DParameters.getK(),
                            true, // STM returns sorted hits
                            true);
                    // 3) perform nucleotide alignments and return edges
                    return hitList.stream()
                            .map(hit -> {
                                var to = hit.getTarget();
                                return new ClonotypeEdgeWithCdr3Alignment<>(
                                        from, to,
                                        getAlignment(from, to)
                                );
                            });
                })
                // NOTE we apply other filtering steps here
                // we state that KNN only applies to CDR3AA alignments,
                // so we do not guarantee KNN in terms of (segment-matched) and similar CDR3NT sequences
                .filter(this::passesFilter);
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

        return substitutions <= scope2DParameters.getNtSearchScope().getMaxSubstitutions() &&
                indels <= scope2DParameters.getNtSearchScope().getMaxIndels() &&
                total <= scope2DParameters.getNtSearchScope().getMaxTotal();
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
        return flatMap(false);
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> parallelStream() {
        return flatMap(true);
    }

    public Scope2DParameters getScope2DParameters() {
        return scope2DParameters;
    }

    public boolean requiresSegmentMatch() {
        return requireSegmentMatch;
    }
}