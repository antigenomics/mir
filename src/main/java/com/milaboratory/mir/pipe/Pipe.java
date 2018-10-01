package com.milaboratory.mir.pipe;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Pipe<T> {
    Stream<T> stream();

    Stream<T> parallelStream();

    default List<T> collect() {
        return stream().collect(Collectors.toList());
    }
}
