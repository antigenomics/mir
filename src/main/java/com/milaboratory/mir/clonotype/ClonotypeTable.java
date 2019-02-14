package com.milaboratory.mir.clonotype;

import com.milaboratory.mir.pipe.Pipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClonotypeTable<T extends Clonotype>
        implements Pipe<ClonotypeCall<T>> {
    private final List<ClonotypeCall<T>> clonotypes;

    public ClonotypeTable(Pipe<ClonotypeCall<T>> clonotypes,
                          boolean assumeUnsorted) {
        this.clonotypes = (assumeUnsorted ? clonotypes.stream().sorted() : clonotypes.stream())
                .collect(Collectors.toList());
    }

    public ClonotypeTable(List<ClonotypeCall<T>> clonotypes) {
        // todo: renormalize
        this.clonotypes = new ArrayList<>();
        boolean unsorted = false;
        long prevCount = Long.MAX_VALUE;

        for (ClonotypeCall<T> c : clonotypes) {
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

    @Override
    public String toString() {
        String res = "[Sample of " + clonotypes.size() + " clonotypes]\n";

        res += clonotypes.stream().limit(10)
                .map(Clonotype::toString).collect(Collectors.joining("\n"));

        return res;
    }
}
