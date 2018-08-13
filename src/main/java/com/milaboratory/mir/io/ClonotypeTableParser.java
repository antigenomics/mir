package com.milaboratory.mir.io;

import com.milaboratory.mir.clonotype.Clonotype;

public interface ClonotypeTableParser<T extends Clonotype> extends ClonotypeParser<T> {
    void readHeader(String header);
}
