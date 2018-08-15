package com.milaboratory.mir.model.generator;


import com.milaboratory.core.sequence.Sequence;

public interface SequenceGenerator<S extends Sequence<S>> {
    S generateForward(int length);
    S generateReverse(int length);
}
