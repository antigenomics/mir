package com.milaboratory.mir.summary.counters;

import com.milaboratory.mir.summary.ClonotypeGroup;
import com.milaboratory.mir.summary.GroupSummaryEntry;

public final class PwmGroupSummaryEntry<G extends ClonotypeGroup> implements GroupSummaryEntry<G> {
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
}
