package com.milaboratory.mir.structure.pdb.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AtomName extends PdbField {
    private static final Map<String, Float> atomicWeights = new ConcurrentHashMap<>();

    static {

    }

    private final float atomicWeight;

    public AtomName(String value) {
        super(value);
        if (value.length() != 4) {
            throw new IllegalArgumentException("Should be 4 characters long");
        }
        this.atomicWeight = atomicWeights.getOrDefault(value, 0.0f);
    }

    public float getAtomicWeight() {
        return atomicWeight;
    }
}
