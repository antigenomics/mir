package com.milaboratory.mir.io;

import com.milaboratory.mir.clonotype.Clonotype;

public interface ClonotypeParser<T extends Clonotype> {
    T parse(String line);
}
