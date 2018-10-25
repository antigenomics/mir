package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcClassType;

public final class MhcComplex implements HeterodimerComplex<MhcRegionType, MhcChain> {
    private final MhcChain firstChain, secondChain;
    private final MhcClassType mhcClassType;

    public MhcComplex(MhcChain firstChain, MhcChain secondChain) {
        boolean correctOrder =
                firstChain.getMhcAllele().getMhcChain().getOrder() <=
                        secondChain.getMhcAllele().getMhcChain().getOrder();

        this.firstChain = correctOrder ? firstChain : secondChain;
        this.secondChain = correctOrder ? secondChain : firstChain;

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
