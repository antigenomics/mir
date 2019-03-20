package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

public class TextFilterAnnotationsValueGetter<T extends Clonotype> implements TextFilterValueGetter<T> {

    private final String key;

    public TextFilterAnnotationsValueGetter(String key) {
        this.key = key;
    }

    @Override
    public String getValue(ClonotypeCall<T> clonotypeCall) {
        return clonotypeCall.getAnnotations().getOrDefault(key, "");
    }
}
