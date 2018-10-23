package com.milaboratory.mir.structure;

public class AntigenReceptor {
    private final AntigenReceptorChain firstChain, secondChain;
    private final AntigenReceptorType antigenReceptorType;

    public AntigenReceptor(AntigenReceptorChain firstChain, AntigenReceptorChain secondChain,
                           AntigenReceptorType antigenReceptorType) {
        this.firstChain = firstChain;
        this.secondChain = secondChain;
        this.antigenReceptorType = antigenReceptorType;
    }
}
