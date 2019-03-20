package com.antigenomics.mir.clonotype.table.filters;

import com.antigenomics.mir.clonotype.Clonotype;

public abstract class TextFilter<T extends Clonotype> extends Filter<T> {

    TextFilter(boolean negative) {
        super(negative);
    }

    protected abstract String getValue();
}
