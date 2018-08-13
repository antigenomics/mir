package com.milaboratory.mir.model.gen;

import com.milaboratory.mir.clonotype.ClonotypeWithRearrangementInfo;

public interface ClonotypeBuilder<T extends RearrangementInfo, U extends ClonotypeWithRearrangementInfo> {
    U build(T rearrangementInfo);
}
