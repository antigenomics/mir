package com.antigenomics.mir.summary;

import com.antigenomics.mir.TableRow;

public interface GroupSummaryEntry<G extends ClonotypeGroup> extends TableRow {
    G getClonotypeGroup();

    double getValue();
}
