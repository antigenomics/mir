package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.core.tree.SequenceTreeMap;
import com.milaboratory.mir.mappers.SequenceMapper;
import com.milaboratory.mir.mappers.SequenceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class StmMapper<T, Q, S extends Sequence<S>>
        implements SequenceMapper<Q, T, S, StmMapperHit<Q, T, S>> {
    private final SequenceTreeMap<S, List<T>> stm;
    private final SequenceProvider<T, S> targetSequenceProvider;
    private final SequenceProvider<Q, S> querySequenceProvider;
    private final SequenceSearchScope searchScope;
    private final ExplicitAlignmentScoring<S> scoring;

    public StmMapper(Iterable<T> clonotypes,
                     SequenceProvider<T, S> targetSequenceProvider,
                     SequenceProvider<Q, S> querySequenceProvider,
                     Alphabet<S> alphabet,
                     SequenceSearchScope searchScope,
                     ExplicitAlignmentScoring<S> scoring) {
        this.stm = new SequenceTreeMap<>(alphabet);
        this.targetSequenceProvider = targetSequenceProvider;
        this.querySequenceProvider = querySequenceProvider;
        // for stream use stream::iterator; N/A for parallel streams
        clonotypes.forEach(this::put);
        this.searchScope = searchScope;
        this.scoring = scoring;
    }

    private void put(T obj) {
        var group = stm.get(targetSequenceProvider.getSequence(obj));
        if (group == null) {
            stm.put(targetSequenceProvider.getSequence(obj), group = new ArrayList<>());
        }
        group.add(obj);
    }

    @Override
    public StmMapperHitList<Q, T, S> map(Q query) {
        var querySeq = querySequenceProvider.getSequence(query);

        // 'search scope' iterator
        var ni = stm.getNeighborhoodIterator(querySeq,
                searchScope.getTreeSearchParameters());

        // buffer for choosing best match
        var groupHitBuffer = new HashMap<S, StmGroupHit<T, S>>();

        // Current list of clonotypes
        List<T> group;

        while ((group = ni.next()) != null) { // until no more alignments found within 'search scope'
            var targetSequence = targetSequenceProvider.getSequence(group.get(0));
            var mutations = ni.getCurrentMutations();

            // need this workaround as it is not possible to implement (ins+dels) <= X scope with tree searcher
            // due to separate insertion and deletion counting
            if (mutations.countOfIndels() > searchScope.getMaxIndels())
                continue;

            // need buffer here as hits are not guaranteed to be ordered
            // by match sequence. using hash map as we'll need to store
            // previous alignmentScore
            var previousGroupHit = groupHitBuffer.get(targetSequence);

            float alignmentScore;
            if (previousGroupHit == null) {
                // introduce a hit if we don't have any previous alignments with this sequence
                alignmentScore = scoring.computeScore(querySeq, mutations);
                groupHitBuffer.put(targetSequence, new StmGroupHit<>(group,
                        targetSequence, alignmentScore, mutations));
            } else if ( // exhaustive search - don't take first hit but try to compare scores
                    searchScope.isExhaustive() &&
                            // if filter is greedy - only consider hits that do not have more mutations than previous hit
                            !(searchScope.isGreedy() &&
                                    previousGroupHit.mutations.size() < mutations.size()) &&
                            // now compute alignment score and replace if its better than previous
                            (alignmentScore = scoring.computeScore(querySeq, mutations)) > previousGroupHit.alignmentScore
            ) {
                groupHitBuffer.put(targetSequence, new StmGroupHit<>(group,
                        targetSequence, alignmentScore, mutations));
            }
        }

        // flatten results
        var hits = new ArrayList<StmMapperHit<Q, T, S>>();
        for (var groupHit : groupHitBuffer.values()) {
            for (var target : groupHit.group) {
                hits.add(new StmMapperHit<>(query, target,
                        groupHit.targetSequence,
                        groupHit.alignmentScore,
                        groupHit.mutations)
                );
            }
        }

        return new StmMapperHitList<>(hits, true);
    }

    @Override
    public SequenceProvider<Q, S> getQuerySequenceProvider() {
        return querySequenceProvider;
    }

    @Override
    public SequenceProvider<T, S> getTargetSequenceProvider() {
        return targetSequenceProvider;
    }

    private static final class StmGroupHit<T, S extends Sequence<S>> {
        final List<T> group;
        final S targetSequence;
        final float alignmentScore;
        final Mutations<S> mutations;

        StmGroupHit(List<T> group, S targetSequence,
                    float alignmentScore,
                    Mutations<S> mutations) {
            this.group = group;
            this.targetSequence = targetSequence;
            this.alignmentScore = alignmentScore;
            this.mutations = mutations;
        }
    }
}
