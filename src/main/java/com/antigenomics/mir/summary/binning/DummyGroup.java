package com.antigenomics.mir.summary.binning;

import com.antigenomics.mir.summary.ClonotypeGroup;

public final class DummyGroup implements ClonotypeGroup {
    public static final DummyGroup INSTANCE = new DummyGroup();

    public static String HEADER = "group";

    private DummyGroup() {
    }

    @Override
    public String asRow() {
        return "dummy";
    }

    @Override
    public String toString() {
        return asRow();
    }
}
