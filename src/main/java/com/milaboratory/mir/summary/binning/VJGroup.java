package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.summary.ClonotypeGroup;

import java.util.Objects;

public class VJGroup implements ClonotypeGroup {
    public static String HEADER = "v\tj";

    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;

    public VJGroup(VariableSegment variableSegment, JoiningSegment joiningSegment) {
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
    }

    public VariableSegment getVariableSegment() {
        return variableSegment;
    }

    public JoiningSegment getJoiningSegment() {
        return joiningSegment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VJGroup vjKey = (VJGroup) o;
        return Objects.equals(variableSegment, vjKey.variableSegment) &&
                Objects.equals(joiningSegment, vjKey.joiningSegment);
    }

    @Override
    public String asRow() {
        return variableSegment.getId() + "\t" + joiningSegment.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableSegment, joiningSegment);
    }

    @Override
    public String toString() {
        return asRow();
    }
}
