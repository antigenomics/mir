package com.antigenomics.mir.probability;


import com.antigenomics.mir.thirdparty.AtomicDouble;

import java.util.HashMap;
import java.util.Set;

public class FixedDistributionAccumulator<T> extends DistributionAccumulator<T> {
    public FixedDistributionAccumulator(Set<T> values) {
        super(new HashMap<>());
        values.forEach(x -> accumulatorMap.put(x, new AtomicDouble()));
    }

    @Override
    public void update(T value, double weight) {
        var counter = accumulatorMap.get(value);
        if (counter != null) {
            counter.addAndGet(weight);
        }
    }

    @Override
    public DistributionAccumulator<T> createCleanInstance() {
        return new FixedDistributionAccumulator<>(values());
    }
}
