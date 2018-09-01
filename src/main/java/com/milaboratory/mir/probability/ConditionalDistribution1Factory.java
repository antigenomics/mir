package com.milaboratory.mir.probability;

@FunctionalInterface
public interface ConditionalDistribution1Factory<C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>> {
    D1 create(D1 distribution, boolean fromAccumulator);

    default D1 create(D1 distribution) {
        return create(distribution, false);
    }

    default D1 create() {
        return create(null);
    }
}
