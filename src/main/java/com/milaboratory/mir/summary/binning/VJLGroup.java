package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Objects;

public class VJLGroup extends VJGroup {
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), len);
    }
}
