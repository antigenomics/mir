package com.milaboratory.mir.structure;

public interface AntigenReceptor {
    AntigenReceptorChain getFirstChain();

    AntigenReceptorChain getSecondChain();

    AntigenReceptorType getAntigenReceptorType();
}
