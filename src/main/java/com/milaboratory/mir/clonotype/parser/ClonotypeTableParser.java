package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;

public interface ClonotypeTableParser<T extends Clonotype> {
    ClonotypeCall<T> parse(String[] splitLine);
}
