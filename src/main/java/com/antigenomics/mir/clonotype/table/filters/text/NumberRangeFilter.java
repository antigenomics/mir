package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;

public class NumberRangeFilter<T extends Clonotype> extends TextFilter<T> {

    private final double numberMin;
    private final double numberMax;

    public NumberRangeFilter(TextFilterValueGetter<T> getter, double numberMin, double numberMax) {
        super(getter);
        this.numberMin = numberMin;
        this.numberMax = numberMax;
    }

    public NumberRangeFilter(TextFilterValueGetter<T> getter, boolean negative, double numberMin, double numberMax) {
        super(getter, negative);
        this.numberMin = numberMin;
        this.numberMax = numberMax;
    }

    public NumberRangeFilter(String key, double numberMin, double numberMax) {
        super(key);
        this.numberMin = numberMin;
        this.numberMax = numberMax;
    }

    public NumberRangeFilter(String key, boolean negative, double numberMin, double numberMax) {
        super(key, negative);
        this.numberMin = numberMin;
        this.numberMax = numberMax;
    }

    @Override
    protected boolean passTextInner(String value) {
        Double aDouble = Double.valueOf(value);
        return aDouble >= numberMin && aDouble <= numberMax;
    }
}
