package com.milaboratory.mir.summary.bin;

import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

import java.util.Objects;

public class VJLKey implements ClonotypeKey {
    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;
    private final int len;

    public VJLKey(VariableSegment variableSegment, JoiningSegment joiningSegment, int len) {
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
        this.len = len;
    }

    public VariableSegment getVariableSegment() {
        return variableSegment;
    }

    public JoiningSegment getJoiningSegment() {
        return joiningSegment;
    }

    public int getLen() {
        return len;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VJLKey that = (VJLKey) o;
        return len == that.len &&
                Objects.equals(variableSegment, that.variableSegment) &&
                Objects.equals(joiningSegment, that.joiningSegment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableSegment, joiningSegment, len);
    }
}
