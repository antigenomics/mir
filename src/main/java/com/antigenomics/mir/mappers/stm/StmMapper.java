package com.antigenomics.mir.mappers.stm;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.core.tree.SequenceTreeMap;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceProvider;
import com.antigenomics.mir.pipe.Pipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StmMapper<T, S extends Sequence<S>>
        implements SequenceMapper<T, S>, Pipe<T> {
    private final SequenceTreeMap<S, List<T>> stm;
    private final SequenceProvider<T, S> sequenceProvider;
    private final SequenceSearchScope searchScope;
    private final ExplicitAlignmentScoring<S> scoring;

    public StmMapper(Iterable<? extends T> objects,
                     SequenceProvider<T, S> sequenceProvider,
                     Alphabet<S> alphabet,
                     SequenceSearchScope searchScope,
                     ExplicitAlignmentScoring<S> scoring) {
        this.stm = new SequenceTreeMap<>(alphabet);
        this.sequenceProvider = sequenceProvider;
        objects.forEach(this::put);
        this.searchScope = searchScope;
        this.scoring = scoring;
    }

    public StmMapper(Stream<? extends T> objects,
                     SequenceProvider<T, S> sequenceProvider,
                     Alphabet<S> alphabet,
                     SequenceSearchScope searchScope,
                     ExplicitAlignmentScoring<S> scoring) {
        if (objects.isParallel()) {
            // Stm is not thread safe
            objects = objects.sequential();
        }
        this.stm = new SequenceTreeMap<>(alphabet);
        this.sequenceProvider = sequenceProvider;
        objects.forEach(this::put);
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

    @Override
    public StmMapperHitList<T, S> map(S query) {
        // 'search scope' iterator
        var ni = stm.getNeighborhoodIterator(query,
                searchScope.getTreeSearchParameters());

        // buffer for choosing best match
        var groupHitBuffer = new HashMap<S, StmGroupHit<T, S>>();

        // Current list of clonotypes
        List<T> group;

        while ((group = ni.next()) != null) { // until no more alignments found within 'search scope'
            var targetSequence = sequenceProvider.getSequence(group.get(0));
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
                alignmentScore = scoring.computeScore(query, mutations);
                groupHitBuffer.put(targetSequence, new StmGroupHit<>(group, alignmentScore, mutations));
            } else if ( // exhaustive search - don't take first hit but try to compare scores
                    searchScope.isExhaustive() &&
                            // if filter is greedy - only consider hits that do not have more mutations than previous hit
                            !(searchScope.isGreedy() &&
                                    previousGroupHit.mutations.size() < mutations.size()) &&
                            // now compute alignment score and replace if its better than previous
                            (alignmentScore = scoring.computeScore(query, mutations)) > previousGroupHit.alignmentScore
            ) {
                groupHitBuffer.put(targetSequence, new StmGroupHit<>(group, alignmentScore, mutations));
            }
        }

        // flatten results
        var hits = new ArrayList<StmMapperHit<T, S>>();
        for (var groupHit : groupHitBuffer.values()) {
            for (var target : groupHit.group) {
                hits.add(new StmMapperHit<>(target,
                        query,
                        groupHit.alignmentScore,
                        groupHit.mutations)
                );
            }
        }

        return new StmMapperHitList<>(hits, true);
    }

    @Override
    public Alphabet<S> getAlphabet() {
        return stm.alphabet;
    }

    @Override
    public SequenceProvider<T, S> getSequenceProvider() {
        return sequenceProvider;
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(stm.values().spliterator(), false).flatMap(Collection::stream);
    }

    @Override
    public Stream<T> parallelStream() {
        return StreamSupport.stream(stm.values().spliterator(), true).flatMap(Collection::stream);
    }

    private static final class StmGroupHit<T, S extends Sequence<S>> {
        final List<T> group;
        final float alignmentScore;
        final Mutations<S> mutations;

        StmGroupHit(List<T> group,
                    float alignmentScore,
                    Mutations<S> mutations) {
            this.group = group;
            this.alignmentScore = alignmentScore;
            this.mutations = mutations;
        }
    }
}
