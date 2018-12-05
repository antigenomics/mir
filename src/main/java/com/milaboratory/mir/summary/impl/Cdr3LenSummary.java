package com.milaboratory.mir.summary.impl;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.summary.ClonotypeGroupSummary;
import com.milaboratory.mir.summary.WrappedClonotype;
import com.milaboratory.mir.thirdparty.AtomicDoubleArray;

import java.util.*;

public abstract class Cdr3LenSummary<T extends Clonotype, G extends ClonotypeGroup>
        implements ClonotypeGroupSummary<T, G, Cdr3LenSummaryEntry<G>> {
    private final G clonotypeGroup;
    private final int maxLen;
    private final AtomicDoubleArray counters;

    public Cdr3LenSummary(G clonotypeGroup, int maxLen) {
        this.clonotypeGroup = clonotypeGroup;
        this.maxLen = maxLen;
        this.counters = new AtomicDoubleArray(maxLen);
    }

    @Override
    public G getClonotypeGroup() {
        return clonotypeGroup;
    }

    @Override
    public Collection<Cdr3LenSummaryEntry<G>> getCounters() {
        var res = new ArrayList<Cdr3LenSummaryEntry<G>>();

        for (int i = 0; i < maxLen; i++) {
            double value = getCount(i);
            if (value > 0) {
                res.add(new Cdr3LenSummaryEntry<>(clonotypeGroup, i, value));
            }
        }

        return res;
    }

    @Override
    public void accept(WrappedClonotype<T, G> wrappedClonotype) {
        double weight = wrappedClonotype.getWeight();
        int len = getLength(wrappedClonotype.getClonotype());
        if (len >= 0) {
            counters.addAndGet(Math.min(len, maxLen - 1), weight);
        }
    }

    protected abstract int getLength(T clonotype);

    public double getCount(int pos) {
        return counters.get(pos);
    }
}
