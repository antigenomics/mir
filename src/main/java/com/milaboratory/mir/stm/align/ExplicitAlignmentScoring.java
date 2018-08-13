package com.milaboratory.mir.stm.align;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Sequence;

public interface ExplicitAlignmentScoring<S extends Sequence<S>> {
    double computeScore(S query,
                        Mutations<S> mutations);
}
