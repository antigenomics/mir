package com.antigenomics.mir.pipe;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Pipe<T> {
    Stream<T> stream();

    default Stream<T> parallelStream() {
        return stream().parallel();
    }

    default List<T> collect() {
        return stream().collect(Collectors.toList());
    }
}
