package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;

public interface ClonotypeTableParserFactory<T extends Clonotype> {
    ClonotypeTableParser<T> create(String[] header);
}
