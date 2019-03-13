package com.antigenomics.mir.summary.impl;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.summary.ClonotypeGroup;
import com.antigenomics.mir.summary.GroupSummaryEntry;

public final class PwmGroupSummaryEntry<G extends ClonotypeGroup> implements GroupSummaryEntry<G> {
    public static String HEADER = "pos\taa\tvalue";

    private final G clonotypeGroup;
    private final byte code;
    private final int pos;
    private final double value;

    public PwmGroupSummaryEntry(G clonotypeGroup, byte code, int pos, double value) {
        this.clonotypeGroup = clonotypeGroup;
        this.code = code;
        this.pos = pos;
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

    public byte getCode() {
        return code;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String asRow() {
        return pos + "\t" + AminoAcidSequence.ALPHABET.codeToSymbol(code) + "\t" + value;
    }

    @Override
    public String toString() {
        return asRow();
    }
}
