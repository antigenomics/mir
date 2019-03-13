package com.antigenomics.mir.probability;

import com.antigenomics.mir.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class ConditionalDistribution1<C1, T,
        D0 extends Distribution<T>> {
    private final Map<C1, D0> probabilityMap;
    private final DistributionFactory<T, D0> innerFactory;

    public ConditionalDistribution1(ConditionalDistribution1<C1, T, D0> toCopy,
                                    DistributionFactory<T, D0> innerFactory,
                                    boolean fromAccumulator) {
        this(CommonUtils.map2map(
                toCopy == null ? // dummy?
                        new HashMap<>() : toCopy.probabilityMap,
                Map.Entry::getKey,
                e -> innerFactory.create(e.getValue(), fromAccumulator)
                ),
                innerFactory,
                true);
    }

    public ConditionalDistribution1(Map<C1, D0> probabilityMap,
                                    DistributionFactory<T, D0> innerFactory,
                                    boolean unsafe) {
        this.innerFactory = innerFactory;
        if (unsafe) {
            this.probabilityMap = probabilityMap;
        } else {
            if (probabilityMap.isEmpty()) {
                throw new IllegalArgumentException("Empty probability map");
            }
            this.probabilityMap = CommonUtils.map2map(
                    probabilityMap,
                    Map.Entry::getKey,
                    e -> innerFactory.create(e.getValue())
            );
        }
    }

    public D0 getDistribution0(C1 condition1) {
        return probabilityMap.getOrDefault(condition1,
                innerFactory.create());
    }
}
