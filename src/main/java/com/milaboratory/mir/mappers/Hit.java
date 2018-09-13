package com.milaboratory.mir.mappers;

public interface Hit<Q, T> extends Comparable<Hit<Q, T>> {
    Q getQuery();

    T getTarget();

    float getScore();

    @Override
    default int compareTo(Hit<Q, T> o) {
        return Float.compare(getScore(), o.getScore());
    }
}
