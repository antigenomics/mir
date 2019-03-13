package com.antigenomics.mir.structure;

import com.antigenomics.mir.mhc.MhcClassType;

public final class MhcComplex implements HeterodimerComplex<MhcRegionType, MhcChain> {
    private final MhcChain firstChain, secondChain;
    private final MhcClassType mhcClassType;

    public MhcComplex(MhcChain firstChain, MhcChain secondChain) {
        boolean correctOrder =
                firstChain.getMhcAllele().getMhcChainType().getOrder() <=
                        secondChain.getMhcAllele().getMhcChainType().getOrder();

        this.firstChain = correctOrder ? firstChain : secondChain;
        this.secondChain = correctOrder ? secondChain : firstChain;

        this.mhcClassType = MhcClassType.combine(firstChain.getMhcAllele().getMhcClassType(),
                secondChain.getMhcAllele().getMhcClassType());
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
