package com.milaboratory.mir.rearrangement.generator;

public interface RearrangmentGenerator<T extends RearrangementInfo> {
    T generate();
}
