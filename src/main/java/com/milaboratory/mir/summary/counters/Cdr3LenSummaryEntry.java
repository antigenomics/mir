package com.milaboratory.mir.summary.counters;

import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.summary.GroupSummaryEntry;

public final class Cdr3LenSummaryEntry<G extends ClonotypeGroup> implements GroupSummaryEntry<G> {
    private final G clonotypeGroup;
    private final int length;
    private final double value;

    public Cdr3LenSummaryEntry(G clonotypeGroup, int length, double value) {
        this.clonotypeGroup = clonotypeGroup;
        this.length = length;
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

    public int getLength() {
        return length;
    }
}
