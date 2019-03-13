package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;

import java.util.Objects;

public final class VJLGroup extends VJGroup {
    public static String HEADER = "v\tj\tlen";

    private final int len;

    public VJLGroup(VariableSegment variableSegment, JoiningSegment joiningSegment, int len) {
        super(variableSegment, joiningSegment);
        this.len = len;
    }

    public int getLen() {
        return len;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VJLGroup vjlKey = (VJLGroup) o;
        return len == vjlKey.len;
    }

    @Override
    public String asRow() {
        return getVariableSegment().getId() + "\t" + getJoiningSegment().getId() + "\t" + len;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), len);
    }

    @Override
    public String toString() {
        return asRow();
    }
}
