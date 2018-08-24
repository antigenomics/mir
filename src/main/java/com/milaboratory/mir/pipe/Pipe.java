package com.milaboratory.mir.pipe;

import java.util.stream.Stream;

public interface Pipe<T> {
    Stream<T> stream();

    Stream<T> parallelStream();
}
