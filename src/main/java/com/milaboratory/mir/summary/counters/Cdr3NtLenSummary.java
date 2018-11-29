package com.milaboratory.mir.summary.counters;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.summary.ClonotypeGroupSummary;
import com.milaboratory.mir.summary.WrappedClonotype;
import com.milaboratory.mir.thirdparty.AtomicDoubleArray;

import java.util.*;

import static java.lang.Math.min;

public final class Cdr3NtLenSummary<T extends Clonotype, G extends ClonotypeGroup>
        implements ClonotypeGroupSummary<T, G, Cdr3NtLenSummaryEntry<G>> {
    public static final int COLS = 90;

    private final G clonotypeGroup;
    private final AtomicDoubleArray counters = new AtomicDoubleArray(COLS);

    public Cdr3NtLenSummary(G clonotypeGroup) {
        this.clonotypeGroup = clonotypeGroup;
    }

    @Override
    public G getClonotypeGroup() {
        return clonotypeGroup;
    }

    @Override
    public Collection<Cdr3NtLenSummaryEntry<G>> getEntries() {
        var res = new ArrayList<Cdr3NtLenSummaryEntry<G>>();

        for (int i = 0; i < COLS; i++) {
            double value = getCount(i);
            if (value > 0) {
                res.add(new Cdr3NtLenSummaryEntry<G>(clonotypeGroup, i, value));
            }
        }

        return res;
    }

    @Override
    public void accept(WrappedClonotype<T, G> wrappedClonotype) {
        NucleotideSequence cdr3Nn = wrappedClonotype.getClonotype().getCdr3Nt();
        double weight = wrappedClonotype.getWeight();
        counters.addAndGet(min(cdr3Nn.size(), COLS-1), weight);

    }


    public double getCount(int pos) {
        return counters.get(pos);
    }
}
