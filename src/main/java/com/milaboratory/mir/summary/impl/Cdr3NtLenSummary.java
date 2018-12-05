package com.milaboratory.mir.summary.impl;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.ClonotypeGroup;

import static java.lang.Math.min;

public final class Cdr3NtLenSummary<T extends Clonotype, G extends ClonotypeGroup>
        extends Cdr3LenSummary<T, G> {
    public Cdr3NtLenSummary(G clonotypeGroup) {
        super(clonotypeGroup, 180);
    }

    @Override
    protected int getLength(T clonotype) {
        return clonotype.getCdr3Nt().size();
    }
}
