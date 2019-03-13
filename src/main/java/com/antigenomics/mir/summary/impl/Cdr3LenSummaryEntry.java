package com.antigenomics.mir.summary.impl;

import com.antigenomics.mir.summary.ClonotypeGroup;
import com.antigenomics.mir.summary.GroupSummaryEntry;

public final class Cdr3LenSummaryEntry<G extends ClonotypeGroup> implements GroupSummaryEntry<G> {
    public static String HEADER = "length\tvalue";

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

    @Override
    public String asRow() {
        return length + "\t" + value;
    }

    @Override
    public String toString() {
        return asRow();
    }
}
