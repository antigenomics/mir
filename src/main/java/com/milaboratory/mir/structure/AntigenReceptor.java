package com.milaboratory.mir.structure;

import com.milaboratory.mir.structure.pdb.ChainRegion;

import java.util.ArrayList;
import java.util.List;

public final class AntigenReceptor implements HeterodimerComplex<AntigenReceptorRegionType, AntigenReceptorChain> {
    private final AntigenReceptorChain firstChain, secondChain;
    private final AntigenReceptorType antigenReceptorType;

    public AntigenReceptor(AntigenReceptorChain firstChain, AntigenReceptorChain secondChain) {
        boolean correctOrder =
                firstChain.getGene().getOrder() <=
                        secondChain.getGene().getOrder();

        this.firstChain = correctOrder ? firstChain : secondChain;
        this.secondChain = correctOrder ? secondChain : firstChain;
        this.antigenReceptorType = AntigenReceptorType.combine(firstChain.getGene(),
                secondChain.getGene());
    }

    @Override
    public AntigenReceptorChain getFirstChain() {
        return firstChain;
    }

    @Override
    public AntigenReceptorChain getSecondChain() {
        return secondChain;
    }

    public AntigenReceptorType getAntigenReceptorType() {
        return antigenReceptorType;
    }
}
