package com.antigenomics.mir.rearrangement.blocks;

@FunctionalInterface
public interface ModelBlock<T extends ModelBlock<T>> {
    T copy(boolean fromAccumulator);

    default T copy() {
        return copy(false);
    }
}
