package com.milaboratory.mir.mappers;

public interface Hit<T> extends Comparable<Hit<T>> {
    T getTarget();

    float getScore();

    @Override
    default int compareTo(Hit<T> o) {
        return Float.compare(getScore(), o.getScore());
    }
}
