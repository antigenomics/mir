package com.milaboratory.mir.summary;

import com.milaboratory.mir.TableRow;

public interface GroupSummaryEntry<G extends ClonotypeGroup> extends TableRow {
    G getClonotypeGroup();

    double getValue();
}
