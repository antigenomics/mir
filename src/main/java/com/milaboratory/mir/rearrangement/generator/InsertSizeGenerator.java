package com.milaboratory.mir.rearrangement.generator;

public interface InsertSizeGenerator {
    int generate();

    double getProbability(int value);
}
