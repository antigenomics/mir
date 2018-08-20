package com.milaboratory.mir.io;

import com.milaboratory.mir.io.BufferedIterator;
import com.milaboratory.mir.io.StreamHelper;

import java.util.Iterator;
import java.util.stream.Stream;

public interface SinglePassEntityProvider<T> extends Iterator<T> {
    T getPoison();

    default Stream<T> asStream() {
        return StreamHelper.asStream(this);
    }

    default Stream<T> asParallelStream() {
        return asParallelStream(32768);
    }

    default Stream<T> asParallelStream(int bufferSize) {
        return StreamHelper.asStream(new BufferedIterator<>(this, getPoison(), bufferSize));
    }
}
