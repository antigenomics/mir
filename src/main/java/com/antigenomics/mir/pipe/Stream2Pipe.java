package com.antigenomics.mir.pipe;

import java.util.stream.Stream;

public class Stream2Pipe<T> implements Pipe<T> {
    private final Stream<T> stream;

    public static <T> Stream2Pipe<T> wrap(Stream<T> stream) {
        return new Stream2Pipe<>(stream);
    }

    private Stream2Pipe(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<T> stream() {
        return stream;
    }
}
