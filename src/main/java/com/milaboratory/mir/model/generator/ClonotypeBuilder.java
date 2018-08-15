package com.milaboratory.mir.model.generator;

import com.milaboratory.mir.clonotype.ClonotypeWithRearrangementInfo;

public interface ClonotypeBuilder<T extends RearrangementInfo, U extends ClonotypeWithRearrangementInfo> {
    U build(T rearrangementInfo);
}
