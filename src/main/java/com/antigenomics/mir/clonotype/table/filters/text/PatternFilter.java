package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;

import java.util.regex.Pattern;

public class PatternFilter<T extends Clonotype> extends TextFilter<T> {

    private final Pattern pattern;

    public PatternFilter(TextFilterValueGetter<T> getter, String pattern) {
        super(getter);
        this.pattern = Pattern.compile(pattern);
    }

    public PatternFilter(TextFilterValueGetter<T> getter, boolean negative, String pattern) {
        super(getter, negative);
        this.pattern = Pattern.compile(pattern);
    }

    public PatternFilter(String key, String pattern) {
        super(key);
        this.pattern = Pattern.compile(pattern);
    }

    public PatternFilter(String key, boolean negative, String pattern) {
        super(key, negative);
        this.pattern = Pattern.compile(pattern);
    }

    public PatternFilter(TextFilterValueGetter<T> getter, Pattern pattern) {
        super(getter);
        this.pattern = pattern;
    }

    public PatternFilter(TextFilterValueGetter<T> getter, boolean negative, Pattern pattern) {
        super(getter, negative);
        this.pattern = pattern;
    }

    public PatternFilter(String key, Pattern pattern) {
        super(key);
        this.pattern = pattern;
    }

    public PatternFilter(String key, boolean negative, Pattern pattern) {
        super(key, negative);
        this.pattern = pattern;
    }

    @Override
    protected boolean passTextInner(String value) {
        return pattern.matcher(value).matches();
    }
}
