package com.milaboratory.mir.structure;

public interface HeterodimerComplex<E extends Enum<E>, C extends StructureChainWithMarkup<E>> {
    C getFirstChain();

    C getSecondChain();
}
