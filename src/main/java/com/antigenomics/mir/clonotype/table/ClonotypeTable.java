package com.antigenomics.mir.clonotype.table;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.pipe.Stream2Pipe;
import com.antigenomics.mir.pipe.Pipe;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Here clonotype table is used as proxy for a RepSeq sample
// same applies to clonotype table pipes
// this means that all classes with "table" in their name are based
// on clonotype calls with count info, not raw clonotypes
public class ClonotypeTable<T extends Clonotype>
        implements Pipe<ClonotypeCall<T>> {
    private final List<ClonotypeCall<T>> clonotypes;

    private static <T extends Clonotype> Pipe<ClonotypeCall<T>> summarise(Pipe<T> clonotypes) {
        var accumulator = new HashMap<T, Long>();
        var counter = new AtomicLong();
        clonotypes.stream().forEach(c -> {
            accumulator.merge(c, 1L, Math::addExact);
            counter.incrementAndGet();
        });
        var total = counter.get();
        var idCounter = new AtomicInteger();
        return Stream2Pipe.wrap(accumulator.entrySet().stream()
                .map(kvp -> {
                    var count = kvp.getValue();
                    return new ClonotypeCall<>(idCounter.incrementAndGet(),
                            count, (double) count / total, kvp.getKey());
                }));
    }

    public ClonotypeTable(Pipe<T> clonotypes) {
        this(summarise(clonotypes), true);
    }

    public ClonotypeTable(Pipe<ClonotypeCall<T>> clonotypeCalls,
                          boolean assumeUnsorted) {
        this.clonotypes = (assumeUnsorted ?
                clonotypeCalls.stream().sorted(Comparator.comparingLong(ClonotypeCall::getCount)) :
                clonotypeCalls.stream())
                .collect(Collectors.toList());
    }

    public ClonotypeTable(List<ClonotypeCall<T>> clonotypeCalls) {
        // todo: renormalize
        this.clonotypes = new ArrayList<>();
        boolean unsorted = false;
        long prevCount = Long.MAX_VALUE;

        for (ClonotypeCall<T> c : clonotypeCalls) {
            long count = c.getCount();
            if (count > prevCount) {
                unsorted = true;
            }
            prevCount = count;
            this.clonotypes.add(c);
        }

        if (unsorted) {
            this.clonotypes.sort(Comparator.comparingLong(ClonotypeCall::getCount));
        }
    }

    @Override
    public Stream<ClonotypeCall<T>> stream() {
        return clonotypes.stream();
    }

    public List<ClonotypeCall<T>> getClonotypes() {
        return Collections.unmodifiableList(clonotypes);
    }

    public int size() {
        return clonotypes.size();
    }

    @Override
    public String toString() {
        String res = "[Sample of " + clonotypes.size() + " clonotypes]\n";

        res += clonotypes.stream().limit(10)
                .map(Clonotype::toString).collect(Collectors.joining("\n"));

        return res;
    }
}
