package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.core.tree.SequenceTreeMap;
import com.milaboratory.mir.mappers.SequenceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class StmSearcher<T, S extends Sequence<S>> {
    private final SequenceTreeMap<S, List<T>> stm;
    private final SequenceProvider<T, S> sequenceProvider;
    private final SequenceSearchScope searchScope;
    private final ExplicitAlignmentScoring<S> scoring;

    public StmSearcher(Iterable<T> clonotypes,
                       SequenceProvider<T, S> sequenceProvider,
                       Alphabet<S> alphabet,
                       SequenceSearchScope searchScope,
                       ExplicitAlignmentScoring<S> scoring) {
        this.stm = new SequenceTreeMap<>(alphabet);
        this.sequenceProvider = sequenceProvider;
        // for stream use stream::iterator; N/A for parallel streams
        clonotypes.forEach(this::put);
        this.searchScope = searchScope;
        this.scoring = scoring;
    }

    private void put(T obj) {
        var group = stm.get(sequenceProvider.getSequence(obj));
        if (group == null) {
            stm.put(sequenceProvider.getSequence(obj), group = new ArrayList<>());
        }
        group.add(obj);
    }

    public List<StmHit<T, S>> search(T query) {
        var querySeq = sequenceProvider.getSequence(query);

        // 'search scope' iterator
        var ni = stm.getNeighborhoodIterator(querySeq,
                searchScope.getTreeSearchParameters());

        // buffer for choosing best match
        var matchBuffer = new HashMap<S, StmGroupHit<T, S>>();

        // Current list of clonotypes
        List<T> group;

        while ((group = ni.next()) != null) { // until no more alignments found within 'search scope'
            var matchSequence = sequenceProvider.getSequence(group.get(0));
            var mutations = ni.getCurrentMutations();

            // need this workaround as it is not possible to implement (ins+dels) <= X scope with tree searcher
            // due to separate insertion and deletion counting
            if (mutations.countOfIndels() > searchScope.getMaxIndels())
                continue;

            var previousResult = matchBuffer.get(matchSequence); // need buffer here as hits are not guaranteed to be ordered
            // by match sequence. using hash map as we'll need to store
            // previous alignmentScore

            if (previousResult == null) {
                // compute new if we don't have any previous alignments with this sequence
                double alignmentScore = scoring.computeScore(querySeq, mutations);
                matchBuffer.put(matchSequence, new StmGroupHit<>(group, alignmentScore, mutations));
            } else if ( // exhaustive search - don't take first hit but try to compare scores
                    searchScope.isExhaustive() &&
                            // if filter is greedy - only consider hits that do not have more mutations than previous hit
                            !(searchScope.isGreedy() &&
                                    previousResult.getMutations().size() < mutations.size())
            ) {
                double alignmentScore = scoring.computeScore(querySeq, mutations);
                if (alignmentScore > previousResult.getAlignmentScore()) {
                    // replace if better alignmentScore
                    matchBuffer.put(matchSequence, new StmGroupHit<>(group, alignmentScore, mutations));
                }
            }
        }

        // flatten results
        var hits = new ArrayList<StmHit<T, S>>();
        for (var groupHit : matchBuffer.values()) {
            for (var target : groupHit.getGroup()) {
                hits.add(new StmHit<>(query, target, groupHit.getAlignmentScore(), groupHit.getMutations()));
            }
        }

        return hits;
    }

    private static final class StmGroupHit<T, S extends Sequence<S>> {
        private final List<T> group;
        private final double alignmentScore;
        private final Mutations<S> mutations;

        StmGroupHit(List<T> group, double alignmentScore,
                    Mutations<S> mutations) {
            this.group = group;
            this.alignmentScore = alignmentScore;
            this.mutations = mutations;
        }

        List<T> getGroup() {
            return group;
        }

        double getAlignmentScore() {
            return alignmentScore;
        }

        Mutations<S> getMutations() {
            return mutations;
        }
    }
}
