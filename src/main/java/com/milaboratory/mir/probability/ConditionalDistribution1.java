package com.milaboratory.mir.probability;

import java.util.Map;

public class ConditionalDistribution1<C1, T,
        D0 extends Distribution<T>> {
    private final Map<C1, D0> embeddedProbs;
    // todo: copy
    // todo: dummy distribution

    public ConditionalDistribution1(Map<C1, D0> embeddedProbs) {
        if (embeddedProbs.isEmpty()) {
            throw new IllegalArgumentException("Empty probability map");
        }
        this.embeddedProbs = embeddedProbs;
    }

    public D0 getDistribution0(C1 condition1) {
        return embeddedProbs.get(condition1);
    }
}
