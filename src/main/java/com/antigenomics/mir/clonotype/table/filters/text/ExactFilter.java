package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;

public class ExactFilter<T extends Clonotype> extends TextFilter<T> {

    final private String exact;
    final private boolean ignoreCase;

    public ExactFilter(TextFilterValueGetter<T> getter, String exact, boolean ignoreCase) {
        super(getter);
        this.exact = exact;
        this.ignoreCase = ignoreCase;
    }

    public ExactFilter(TextFilterValueGetter<T> getter, boolean negative, String exact, boolean ignoreCase) {
        super(getter, negative);
        this.exact = exact;
        this.ignoreCase = ignoreCase;
    }

    public ExactFilter(String key, String exact, boolean ignoreCase) {
        super(key);
        this.exact = exact;
        this.ignoreCase = ignoreCase;
    }

    public ExactFilter(String key, boolean negative, String exact, boolean ignoreCase) {
        super(key, negative);
        this.exact = exact;
        this.ignoreCase = ignoreCase;
    }

    public ExactFilter(TextFilterValueGetter<T> getter, String exact) {
        this(getter, exact, false);
    }

    public ExactFilter(TextFilterValueGetter<T> getter, boolean negative, String exact) {
        this(getter, negative, exact, false);
    }

    public ExactFilter(String key, String exact) {
        this(key, exact, false);
    }

    public ExactFilter(String key, boolean negative, String exact) {
        this(key, negative, exact, false);
    }

    @Override
    protected boolean passTextInner(String value) {
        return ignoreCase ? value.equalsIgnoreCase(exact) : value.equals(exact);
    }
}
