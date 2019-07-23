package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;

public class LengthFilter<T extends Clonotype> extends TextFilter<T> {

    private final int lengthMin;
    private final int lengthMax;

    public LengthFilter(TextFilterValueGetter<T> getter, int lengthMin, int lengthMax) {
        super(getter);
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }

    public LengthFilter(TextFilterValueGetter<T> getter, boolean negative, int length) {
        super(getter, negative);
        this.lengthMin = length;
        this.lengthMax = length;
    }

    public LengthFilter(TextFilterValueGetter<T> getter, boolean negative, int lengthMin, int lengthMax) {
        super(getter, negative);
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }

    public LengthFilter(String key, int lengthMin, int lengthMax) {
        super(key);
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }

    public LengthFilter(String key, int length) {
        super(key);
        this.lengthMin = length;
        this.lengthMax = length;
    }

    public LengthFilter(String key, boolean negative, int lengthMin, int lengthMax) {
        super(key, negative);
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }

    @Override
    protected boolean passTextInner(String value) {
        final int valueLength = value.length();
        return valueLength >= lengthMin && valueLength <= lengthMax;
    }


}
