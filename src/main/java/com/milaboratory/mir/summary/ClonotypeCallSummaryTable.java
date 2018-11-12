package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;

public class ClonotypeCallSummaryTable<T extends Clonotype, K extends ClonotypeKey,
        C extends ClonotypeCounter<ClonotypeCall<T>, K>> extends ClonotypeSummaryTable<ClonotypeCall<T>, K, C> {
    private final boolean weightByFrequency;

    public ClonotypeCallSummaryTable(ClonotypeBinner<ClonotypeCall<T>, K> clonotypeBinner,
                                     ClonotypeCounterFactory<ClonotypeCall<T>, K, C> clonotypeCounterFactory,
                                     boolean weightByFrequency) {
        super(clonotypeBinner, clonotypeCounterFactory,
                weightByFrequency ?
                        subject -> subject :
                        subject -> subject.scaleWeight(subject.getClonotype().getFrequency()));
        this.weightByFrequency = weightByFrequency;
    }

    public boolean weightByFrequency() {
        return weightByFrequency;
    }
}
