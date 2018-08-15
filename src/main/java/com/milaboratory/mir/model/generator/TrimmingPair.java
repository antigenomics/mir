package com.milaboratory.mir.model.generator;

public class TrimmingPair {
    private final int fivePrime, threePrime;

    public TrimmingPair(int fivePrime, int threePrime) {
        this.fivePrime = fivePrime;
        this.threePrime = threePrime;
    }

    public int getFivePrime() {
        return fivePrime;
    }

    public int getThreePrime() {
        return threePrime;
    }
}
