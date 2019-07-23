package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

import java.util.List;

public interface TextFilterValueGetter<T extends Clonotype> {

    String getValue(ClonotypeCall<T> clonotypeCall);

    default boolean isSingleValue() {
        return true;
    }

    default List<String> getValues(ClonotypeCall<T> clonotypeCall) {
        return List.of(getValue(clonotypeCall));
    }

}
