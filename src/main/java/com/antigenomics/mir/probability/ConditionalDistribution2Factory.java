package com.antigenomics.mir.probability;

@FunctionalInterface
public interface ConditionalDistribution2Factory<C2, C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>,
        D2 extends ConditionalDistribution2<C2, C1, T, D0, D1>> {
    D2 create(D2 distribution, boolean fromAccumulator);

    default D2 create(D2 distribution) {
        return create(distribution, false);
    }

    default D2 create() {
        return create(null);
    }
}
