package com.milaboratory.mir.model.parser;

import com.milaboratory.mir.model.generator.RearrangmentGenerator;

public interface RearrangementGeneratorFactory<T extends RearrangmentGenerator> {
    T create();
}
