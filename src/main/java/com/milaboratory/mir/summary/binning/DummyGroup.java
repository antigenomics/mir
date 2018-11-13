package com.milaboratory.mir.summary.binning;

import com.milaboratory.mir.summary.ClonotypeGroup;

public final class DummyGroup implements ClonotypeGroup {
    public static final DummyGroup INSTANCE = new DummyGroup();

    private DummyGroup() {
    }
}
