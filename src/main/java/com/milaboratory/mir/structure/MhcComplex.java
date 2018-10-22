package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcClassType;

public interface MhcComplex {
    MhcChain getFirstChain();

    MhcChain getSecondChain();

    MhcClassType getMhcClassType();
}
