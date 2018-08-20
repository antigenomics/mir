package com.milaboratory.mir.io;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamHelper {
    public static <T> Stream<T> asStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> asStream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> asParallelStream(Iterable<T> iterable, T poison, int bufferSize) {
        return asParallelStream(iterable.iterator(), poison, bufferSize);
    }

    public static <T> Stream<T> asParallelStream(Iterator<T> iterator, T poison, int bufferSize) {
        return asParallelStream(new BufferedIterator<>(iterator, poison, bufferSize));
    }

    public static <T> Stream<T> asParallelStream(BufferedIterator<T> iterator) {
        return StreamSupport.stream(parallelSpliterator(iterator), true);
    }

    private static <T> Spliterator<T> parallelSpliterator(BufferedIterator<T> bufferedIterator) {
        return Spliterators.spliterator(bufferedIterator,
               0, // (long) bufferedIterator.getBufferSize(), // WTF??
                Spliterator.CONCURRENT | Spliterator.NONNULL | Spliterator.ORDERED);
    }
}
