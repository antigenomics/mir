package com.milaboratory.mir.io;

import com.milaboratory.mir.model.gen.RearrangmentGenerator;

public interface RearrangementGeneratorFactory<T extends RearrangmentGenerator> {
    T create();
}
