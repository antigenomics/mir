package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcClassType;
import com.milaboratory.mir.mhc.MhcRegionType;

public class MhcComplex implements HeterodimerComplex<MhcRegionType, MhcChain> {
    private final MhcChain firstChain, secondChain;
    private final MhcClassType mhcClassType;

    public MhcComplex(MhcChain firstChain, MhcChain secondChain) {
        this.firstChain = firstChain;
        this.secondChain = secondChain;
        this.mhcClassType = MhcClassType.combine(firstChain.getMhcAllele().getMhcClass(),
                secondChain.getMhcAllele().getMhcClass());
    }

    @Override
    public MhcChain getFirstChain() {
        return firstChain;
    }

    @Override
    public MhcChain getSecondChain() {
        return secondChain;
    }

    public MhcClassType getMhcClassType() {
        return mhcClassType;
    }
}
