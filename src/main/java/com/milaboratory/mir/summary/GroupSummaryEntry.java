package com.milaboratory.mir.summary;

public interface GroupSummaryEntry<G extends ClonotypeGroup> {
    G getClonotypeGroup();

    double getValue();
}
