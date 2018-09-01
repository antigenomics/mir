package com.milaboratory.mir.probability;

import com.milaboratory.mir.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class ConditionalDistribution2<C2, C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>> {
    private final Map<C2, D1> probabilityMap;
    private final ConditionalDistribution1Factory<C1, T, D0, D1> innerFactory;

    public ConditionalDistribution2(ConditionalDistribution2<C2, C1, T, D0, D1> toCopy,
                                    ConditionalDistribution1Factory<C1, T, D0, D1> factory,
                                    boolean fromAccumulator) {
        this(CommonUtils.map2map(
                toCopy == null ? new HashMap<>() : toCopy.probabilityMap,
                Map.Entry::getKey,
                e -> factory.create(e.getValue(), fromAccumulator)
                ),
                factory,
                true);
    }

    public ConditionalDistribution2(Map<C2, D1> probabilityMap,
                                    ConditionalDistribution1Factory<C1, T, D0, D1> innerFactory,
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


    public D1 getDistribution1(C2 condition2) {
        return probabilityMap.getOrDefault(condition2, innerFactory.create());
    }

    public D0 getDistribution0(C2 condition2, C1 condition1) {
        return getDistribution1(condition2).getDistribution0(condition1);
    }
}
