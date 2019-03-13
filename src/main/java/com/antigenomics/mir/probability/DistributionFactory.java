package com.antigenomics.mir.probability;

@FunctionalInterface
public interface DistributionFactory<T, D0 extends Distribution<T>> {
    D0 create(D0 distribution, boolean fromAccumulator);

    default D0 create(D0 distribution) {
        return create(distribution, false);
    }

    default D0 create() {
        return create(null);
    }
}
