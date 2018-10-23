package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcClassType;

public class MhcComplex {
    private final MhcChain firstChain, secondChain;
    private final MhcClassType mhcClassType;

    public MhcComplex(MhcChain firstChain, MhcChain secondChain, MhcClassType mhcClassType) {
        this.firstChain = firstChain;
        this.secondChain = secondChain;
        this.mhcClassType = mhcClassType;
    }

    public MhcChain getFirstChain() {
        return firstChain;
    }

    public MhcChain getSecondChain() {
        return secondChain;
    }

    public MhcClassType getMhcClassType() {
        return mhcClassType;
    }
}
