package com.milaboratory.mir.summary.counters;

import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.summary.GroupSummaryEntry;

public final class Cdr3NtLenSummaryEntry<G extends ClonotypeGroup> implements GroupSummaryEntry<G> {
    private final G clonotypeGroup;
    private final int lengths;
    private final double value;

    public Cdr3NtLenSummaryEntry(G clonotypeGroup, int lengths, double value) {
        this.clonotypeGroup = clonotypeGroup;
        this.lengths = lengths;
        this.value = value;
    }

    @Override
    public G getClonotypeGroup() {
        return clonotypeGroup;
    }

    @Override
    public double getValue() {
        return value;
    }


    public int getLengths() {
        return lengths;
    }
}
