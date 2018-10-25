package com.milaboratory.mir.structure;

public class AntigenReceptor implements HeterodimerComplex<AntigenReceptorRegionType, AntigenReceptorChain> {
    private final AntigenReceptorChain firstChain, secondChain;
    private final AntigenReceptorType antigenReceptorType;

    public AntigenReceptor(AntigenReceptorChain firstChain, AntigenReceptorChain secondChain) {
        boolean correctOrder =
                firstChain.getAntigenReceptorChainType().getOrder() <=
                secondChain.getAntigenReceptorChainType().getOrder();

        this.firstChain = correctOrder ? firstChain : secondChain;
        this.secondChain = correctOrder ? secondChain : firstChain;
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
