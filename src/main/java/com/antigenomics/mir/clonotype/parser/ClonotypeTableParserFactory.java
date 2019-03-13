package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.Clonotype;

public interface ClonotypeTableParserFactory<T extends Clonotype> {
    ClonotypeTableParser<T> create(String[] header);
}
