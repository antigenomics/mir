package com.antigenomics.mir.mappers;

public interface Mapper<Q, T, H extends Hit<T>> {
    HitList<? extends H> map(Q query);
}
