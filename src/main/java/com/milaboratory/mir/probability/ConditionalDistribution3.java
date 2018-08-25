package com.milaboratory.mir.probability;

import java.util.Map;

public class ConditionalDistribution3<C3, C2, C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>,
        D2 extends ConditionalDistribution2<C2, C1, T, D0, D1>> {
    private final Map<C3, D2> embeddedProbs;

    public ConditionalDistribution3(Map<C3, D2> embeddedProbs) {
        this.embeddedProbs = embeddedProbs;
    }

    public D2 getDistribution2(C3 condition3) {
        return embeddedProbs.get(condition3);
    }

    public D1 getDistribution1(C3 condition3, C2 condition2) {
        return getDistribution2(condition3).getDistribution1(condition2);
    }

    public D0 getDistribution0(C3 condition3, C2 condition2, C1 condition1) {
        return getDistribution2(condition3).getDistribution1(condition2).getDistribution0(condition1);
    }
}
