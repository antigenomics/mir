package com.antigenomics.mir.structure;

@FunctionalInterface
public interface HeterodimerComplexFactory<E extends Enum<E>,
        C extends StructureChainWithMarkup<E>,
        O extends HeterodimerComplex<E, C>> {
    O create(C firstChain, C secondChain);
}
