package com.milaboratory.mir.rearrangement.blocks;

public interface Block<T extends Block<T>> {
    T copyBlock();
}
