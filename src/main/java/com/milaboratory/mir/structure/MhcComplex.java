package com.milaboratory.mir.structure;

public interface MhcComplex {
    MhcChain getFirstChain();

    MhcChain getSecondChain();

    MhcClassType getMhcClassType();
}
