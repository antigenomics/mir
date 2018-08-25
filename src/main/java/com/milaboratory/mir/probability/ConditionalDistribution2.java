package com.milaboratory.mir.probability;

import java.util.Map;

public class ConditionalDistribution2<C2, C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>> {
    private final Map<C2, D1> embeddedProbs;

    public ConditionalDistribution2(Map<C2, D1> embeddedProbs) {
        this.embeddedProbs = embeddedProbs;
    }

    public D1 getDistribution1(C2 condition2) {
        return embeddedProbs.get(condition2);
    }

    public D0 getDistribution0(C2 condition2, C1 condition1) {
        return embeddedProbs.get(condition2).getDistribution0(condition1);
    }
}
