package com.milaboratory.mir.graph;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.pipe.Pipe;
import com.milaboratory.mir.pipe.Stream2Pipe;
import com.milaboratory.mir.summary.ClonotypeGroupWrapper;
import com.milaboratory.mir.summary.WrappedClonotype;
import com.milaboratory.mir.summary.binning.SimpleVJGroupWrapper;
import com.milaboratory.mir.summary.binning.VJGroup;
import com.milaboratory.mir.summary.binning.VJGroupWrapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// NOTE This class is intended to be used for B-cell data
// here we can't do processing on-the-fly and have to sink clonotype pipe
// We then go by the set of V segments and in embedded loop we go by J segments
// We create Cdr3NtScopedGraph for each VJ combination and flatten the results
// IMPORTANT we use only best (by call score) VJ combination
public class VJGroupedCdr3NtScopedGraph<T extends Clonotype>
        implements Pipe<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> {
    private final Scope2DParameters scope2DParameters;
    private final Pipe<T> clonotypes;
    private final ClonotypeGroupWrapper<T, VJGroup> wrapper;

    public VJGroupedCdr3NtScopedGraph(Pipe<T> clonotypes,
                                      Scope2DParameters scope2DParameters,
                                      boolean fuzzyVJ) {
        this.clonotypes = clonotypes;
        this.scope2DParameters = scope2DParameters;
        this.wrapper = fuzzyVJ ? new VJGroupWrapper<>() : new SimpleVJGroupWrapper<>();
    }

    private Collection<List<WrappedClonotype<T, VJGroup>>> createGroups() {
        return clonotypes.stream()
                .map(wrapper::create)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(WrappedClonotype::getGroup))
                .values();
    }

    private Cdr3NtScopedGraph<T> createGraph(
            List<WrappedClonotype<T, VJGroup>> groupedClonotypes) {
        var pipe = Stream2Pipe.wrap(groupedClonotypes.stream().map(WrappedClonotype::getClonotype));
        return new Cdr3NtScopedGraph<>(
                pipe, scope2DParameters, false
        );
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> stream() {
        return createGroups().stream().flatMap(x -> createGraph(x).stream());
    }

    @Override
    public Stream<ClonotypeEdgeWithCdr3Alignment<T, NucleotideSequence>> parallelStream() {
        return createGroups().stream().flatMap(x -> createGraph(x).parallelStream());
    }
}
