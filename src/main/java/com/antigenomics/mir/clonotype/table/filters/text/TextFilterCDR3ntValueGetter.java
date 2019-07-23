package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

public class TextFilterCDR3ntValueGetter<T extends Clonotype> implements TextFilterValueGetter<T> {

    @Override
    public String getValue(ClonotypeCall<T> clonotypeCall) {
        return clonotypeCall.getCdr3Nt().toString();
    }
}
