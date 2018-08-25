package com.milaboratory.mir.probability;

import com.milaboratory.mir.thirdparty.AtomicDouble;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentDistributionAccumulator<T> extends DistributionAccumulator<T> {
    public ConcurrentDistributionAccumulator() {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public void update(T value, double weight) {
        accumulatorMap.computeIfAbsent(value, x -> new AtomicDouble()).addAndGet(weight);
    }

    @Override
    public DistributionAccumulator<T> createCleanInstance() {
        return new ConcurrentDistributionAccumulator<>();
    }
}
