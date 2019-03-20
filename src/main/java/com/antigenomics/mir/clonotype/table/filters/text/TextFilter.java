package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.clonotype.table.filters.Filter;

public abstract class TextFilter<T extends Clonotype> extends Filter<T> {

    final TextFilterValueGetter<T> getter;

    TextFilter(TextFilterValueGetter<T> getter) {
        this(getter, false);
    }

    TextFilter(TextFilterValueGetter<T> getter, boolean negative) {
        super(negative);
        this.getter = getter;
    }

    TextFilter(String key) {
        this(key, false);
    }

    TextFilter(String key, boolean negative) {
        super(negative);
        this.getter = convertKeyToValueGetter(key);
    }

    @Override
    protected boolean passInner(ClonotypeCall<T> clonotypeCall) {
        return this.getter.isSingleValue() ?
                this.passTextInner(this.getter.getValue(clonotypeCall)) :
                this.getter.getValues(clonotypeCall).stream().anyMatch(this::passTextInner);
    }

    protected abstract boolean passTextInner(String value);

    private TextFilterValueGetter<T> convertKeyToValueGetter(String key) {
        switch (key.toLowerCase()) {
            case "cdr3aa":
                return new TextFilterCDR3aaValueGetter<>();
            case "cdr3nt":
                return new TextFilterCDR3ntValueGetter<>();
            case "v.segm":
                return new TextFilterVSegmentValueGetter<>();
            case "j.segm":
                return new TextFilterJSegmentValueGetter<>();
            case "d.segm":
                return new TextFilterDSegmentValueGetter<>();
            case "c.segm":
                return new TextFilterCSegmentValueGetter<>();
            default:
                return new TextFilterAnnotationsValueGetter<>(key);
        }
    }

}
