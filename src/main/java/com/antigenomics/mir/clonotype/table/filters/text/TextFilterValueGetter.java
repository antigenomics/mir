package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

public interface TextFilterValueGetter<T extends Clonotype> {

    String getValue(ClonotypeCall<T> clonotypeCall);

}
