package com.milaboratory.mir.probability;

import com.milaboratory.mir.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class ConditionalDistribution3<C3, C2, C1, T,
        D0 extends Distribution<T>,
        D1 extends ConditionalDistribution1<C1, T, D0>,
        D2 extends ConditionalDistribution2<C2, C1, T, D0, D1>> {
    private final Map<C3, D2> probabilityMap;
    private final ConditionalDistribution2Factory<C2, C1, T, D0, D1, D2> innerFactory;

    public ConditionalDistribution3(ConditionalDistribution3<C3, C2, C1, T, D0, D1, D2> toCopy,
                                    ConditionalDistribution2Factory<C2, C1, T, D0, D1, D2> innerFactory,
                                    boolean fromAccumulator) {
        this(CommonUtils.map2map(
                toCopy == null ? new HashMap<>() : toCopy.probabilityMap,
                Map.Entry::getKey,
                e -> innerFactory.create(e.getValue(), fromAccumulator)
                ),
                innerFactory,
                true);
    }

    public ConditionalDistribution3(Map<C3, D2> probabilityMap,
                                    ConditionalDistribution2Factory<C2, C1, T, D0, D1, D2> innerFactory,
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

    public D2 getDistribution2(C3 condition3) {
        return probabilityMap.getOrDefault(condition3, innerFactory.create());
    }

    public D1 getDistribution1(C3 condition3, C2 condition2) {
        return getDistribution2(condition3).getDistribution1(condition2);
    }

    public D0 getDistribution0(C3 condition3, C2 condition2, C1 condition1) {
        return getDistribution1(condition3, condition2).getDistribution0(condition1);
    }
}
