package com.antigenomics.mir.clonotype.table.filters;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;

public abstract class Filter<T extends Clonotype> {

    final private boolean negative;

    public Filter(boolean negative) {
        this.negative = negative;
    }

    boolean pass(ClonotypeCall<T> clonotypeCall) {
        return negative ? !passInner(clonotypeCall) : passInner(clonotypeCall);
    }

    boolean isNegative() {
        return negative;
    }

    protected abstract boolean passInner(ClonotypeCall<T> clonotypeCall);

}
