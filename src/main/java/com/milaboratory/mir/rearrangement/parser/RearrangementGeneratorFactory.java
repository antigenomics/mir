package com.milaboratory.mir.rearrangement.parser;

import com.milaboratory.mir.rearrangement.generator.RearrangmentGenerator;

public interface RearrangementGeneratorFactory<T extends RearrangmentGenerator> {
    T create();
}
