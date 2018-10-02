package com.milaboratory.mir.graph;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.mappers.stm.DummyExplicitAlignmentScoring;
import com.milaboratory.mir.mappers.stm.SequenceSearchScope;
import com.milaboratory.mir.mappers.stm.StmMapper;
import com.milaboratory.mir.pipe.Pipe;

import java.util.stream.Stream;

public class Cdr3NtScopedGraph<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> {
    private final StmMapper<T, NucleotideSequence> stmMapper;
    private final int k;

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             SequenceSearchScope searchScope) {
        this.stmMapper = new StmMapper<>(
                clonotypes.stream(),
                Clonotype::getCdr3Nt,
                NucleotideSequence.ALPHABET,
                searchScope,
                DummyExplicitAlignmentScoring.instance()
        );
        this.k = k;
    }

    public Cdr3NtScopedGraph(Pipe<T> clonotypes,
                             int k,
                             int maxSubstitutions, int maxIndels, int maxTotal) {
        this(clonotypes, k, new SequenceSearchScope(maxSubstitutions, maxIndels, maxTotal,
                false, true));
    }

    private Stream<ClonotypeEdgeWithAlignment<T, NucleotideSequence>> flatMap(Stream<T> stream) {
        return stream
                .flatMap(from ->
                        stmMapper
                                .map(from.getCdr3Nt())
                                .getHits()
                                .stream()
                                .limit(k)
                                .map(hit ->
                                        new ClonotypeEdgeWithAlignment<>(from, hit.getTarget(), hit.getAlignment())));
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