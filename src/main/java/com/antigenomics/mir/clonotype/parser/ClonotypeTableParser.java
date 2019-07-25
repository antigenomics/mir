package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

public interface ClonotypeTableParser<T extends Clonotype> {
    String[] getHeader();

    ClonotypeCall<T> parse(String[] splitLine);
}
