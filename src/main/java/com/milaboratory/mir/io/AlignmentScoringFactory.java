package com.milaboratory.mir.io;

import com.milaboratory.mir.stm.align.ExplicitAlignmentScoring;

public interface AlignmentScoringFactory<T extends ExplicitAlignmentScoring> {
    T create();
}
