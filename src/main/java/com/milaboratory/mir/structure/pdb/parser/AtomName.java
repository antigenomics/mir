package com.milaboratory.mir.structure.pdb.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AtomName extends PdbField {
    private static final Map<Character, Double> atomicWeights = new ConcurrentHashMap<>();

    static {
        atomicWeights.put('C', 12.011);
        atomicWeights.put('N', 14.007);
        atomicWeights.put('O', 15.999);
        atomicWeights.put('S', 32.060);
    }

    private final double atomicWeight;
    private final Character letter;

    public AtomName(String value) {
        super(value);
        if (value.length() != 4) {
            throw new IllegalArgumentException("Should be 4 characters long");
        }
        this.letter = value.charAt(1);
        this.atomicWeight = atomicWeights.getOrDefault(letter, 0.0d);
    }

    public double getAtomicWeight() {
        return atomicWeight;
    }

    public Character getLetter() {
        return letter;
    }
}
