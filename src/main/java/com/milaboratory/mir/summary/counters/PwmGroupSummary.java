package com.milaboratory.mir.summary.counters;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.WrappedClonotype;
import com.milaboratory.mir.summary.ClonotypeGroupSummary;
import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.thirdparty.AtomicDoubleArray;

import java.util.*;

public final class PwmGroupSummary<T extends Clonotype, G extends ClonotypeGroup>
        implements ClonotypeGroupSummary<T, G, PwmGroupSummaryEntry<G>> {
    public static final byte ROWS = (byte) AminoAcidSequence.ALPHABET.size();
    public static final int COLS = 90;

    private final G clonotypeGroup;
    private final AtomicDoubleArray counters = new AtomicDoubleArray(ROWS * COLS);

    public PwmGroupSummary(G clonotypeGroup) {
        this.clonotypeGroup = clonotypeGroup;
    }

    @Override
    public G getClonotypeGroup() {
        return clonotypeGroup;
    }

    @Override
    public Collection<PwmGroupSummaryEntry<G>> getEntries() {
        var res = new ArrayList<PwmGroupSummaryEntry<G>>();

        for (byte i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                double value = getCount(i, j);
                if (value > 0) {
                    res.add(new PwmGroupSummaryEntry<G>(clonotypeGroup, i, j, value));
                }
            }
        }

        return res;
    }

    @Override
    public void accept(WrappedClonotype<T, G> wrappedClonotype) {
        AminoAcidSequence cdr3Aa = wrappedClonotype.getClonotype().getCdr3Aa();
        if (cdr3Aa.size() <= COLS) {
            double weight = wrappedClonotype.getWeight();
            for (int i = 0; i < cdr3Aa.size(); i++) {
                int index = computeIndex(cdr3Aa.codeAt(i), i);
                counters.addAndGet(index, weight);
            }
        }
    }

    private int computeIndex(byte aa, int pos) {
        return aa + pos * ROWS;
    }

    public double getCount(byte aa, int pos) {
        return counters.get(computeIndex(aa, pos));
    }
}
