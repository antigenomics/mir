package com.milaboratory.mir.structure.pdb.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AtomName extends PdbField {
    private static final Map<Character, Float> atomicWeights = new ConcurrentHashMap<>();

    static {
        atomicWeights.put('C', 12.011f);
        atomicWeights.put('N', 14.007f);
        atomicWeights.put('O', 15.999f);
        atomicWeights.put('S', 32.06f);
    }

    private final float atomicWeight;

    public AtomName(String value) {
        super(value);
        if (value.length() != 4) {
            throw new IllegalArgumentException("Should be 4 characters long");
        }
        this.atomicWeight = atomicWeights.getOrDefault(value.charAt(1), 0.0f);
    }

    public float getAtomicWeight() {
        return atomicWeight;
    }
}
