package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;

public class SubstringFilter<T extends Clonotype> extends TextFilter<T> {

    final private String substring;
    final private boolean ignoreCase;

    public SubstringFilter(TextFilterValueGetter<T> getter, String substring, boolean ignoreCase) {
        super(getter);
        this.substring = substring;
        this.ignoreCase = ignoreCase;
    }

    public SubstringFilter(TextFilterValueGetter<T> getter, boolean negative, String substring, boolean ignoreCase) {
        super(getter, negative);
        this.substring = substring;
        this.ignoreCase = ignoreCase;
    }

    public SubstringFilter(String key, String substring, boolean ignoreCase) {
        super(key);
        this.substring = substring;
        this.ignoreCase = ignoreCase;
    }

    public SubstringFilter(String key, boolean negative, String substring, boolean ignoreCase) {
        super(key, negative);
        this.substring = substring;
        this.ignoreCase = ignoreCase;
    }

    public SubstringFilter(TextFilterValueGetter<T> getter, String substring) {
        this(getter, substring, false);
    }

    public SubstringFilter(TextFilterValueGetter<T> getter, boolean negative, String substring) {
        this(getter, negative, substring, false);
    }

    public SubstringFilter(String key, String substring) {
        this(key, substring, false);
    }

    public SubstringFilter(String key, boolean negative, String substring) {
        this(key, negative, substring, false);
    }

    @Override
    protected boolean passTextInner(String value) {
        return ignoreCase ? value.toLowerCase().contains(substring.toLowerCase()) : value.contains(substring);
    }
}
