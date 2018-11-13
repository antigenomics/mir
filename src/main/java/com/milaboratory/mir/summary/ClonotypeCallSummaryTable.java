package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;

public final class ClonotypeCallSummaryTable<T extends Clonotype, G extends ClonotypeGroup, E extends GroupSummaryEntry<G>,
        C extends ClonotypeGroupSummary<ClonotypeCall<T>, G, E>> extends ClonotypeSummaryTable<ClonotypeCall<T>, G, E, C> {
    private final boolean weightByFrequency;

    public ClonotypeCallSummaryTable(ClonotypeGroupWrapper<ClonotypeCall<T>, G> clonotypeGroupWrapper,
                                     ClonotypeGroupSummaryFactory<ClonotypeCall<T>, G, E, C> clonotypeGroupSummaryFactory,
                                     boolean weightByFrequency) {
        super(clonotypeGroupWrapper, clonotypeGroupSummaryFactory,
                weightByFrequency ?
                        subject -> subject :
                        subject -> subject.scaleWeight(subject.getClonotype().getFrequency()));
        this.weightByFrequency = weightByFrequency;
    }

    public boolean weightByFrequency() {
        return weightByFrequency;
    }
}
