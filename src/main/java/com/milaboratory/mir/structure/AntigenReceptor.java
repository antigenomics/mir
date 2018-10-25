package com.milaboratory.mir.structure;

public class AntigenReceptor implements HeterodimerComplex<AntigenReceptorRegionType, AntigenReceptorChain> {
    private final AntigenReceptorChain firstChain, secondChain;
    private final AntigenReceptorType antigenReceptorType;

    public AntigenReceptor(AntigenReceptorChain firstChain, AntigenReceptorChain secondChain) {
        this.firstChain = firstChain;
        this.secondChain = secondChain;
        this.antigenReceptorType = AntigenReceptorType.combine(firstChain.getAntigenReceptorChainType(),
                secondChain.getAntigenReceptorChainType());
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
