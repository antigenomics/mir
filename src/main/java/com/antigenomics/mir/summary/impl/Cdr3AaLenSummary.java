package com.antigenomics.mir.summary.impl;

import com.antigenomics.mir.clonotype.Clonotype;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.SequenceUtils;
import com.antigenomics.mir.summary.ClonotypeGroup;

public final class Cdr3AaLenSummary<T extends Clonotype, G extends ClonotypeGroup>
        extends Cdr3LenSummary<T, G> {
    public Cdr3AaLenSummary(G clonotypeGroup) {
        super(clonotypeGroup, 60);
    }

    @Override
    protected int getLength(T clonotype) {
        AminoAcidSequence cdr3Aa = clonotype.getCdr3Aa();
        return SequenceUtils.isNonCoding(cdr3Aa) ? -1 : cdr3Aa.size();
    }
}
