package com.antigenomics.mir.structure.pdb.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class PdbFieldCache<T extends PdbField> {
    private final Map<String, T> cache = new ConcurrentHashMap<>();
    private final Function<String, T> factory;

    public PdbFieldCache(Function<String, T> factory) {
        this.factory = factory;
    }

    public T createField(String string) {
        return cache.computeIfAbsent(string, factory);
    }
}
