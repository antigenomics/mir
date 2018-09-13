package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

public interface Mapper<Q, T, H extends Hit<Q, T>> {
    HitList<H> map(Q query);
}
