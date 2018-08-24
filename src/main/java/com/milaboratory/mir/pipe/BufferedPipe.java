package com.milaboratory.mir.pipe;

public interface BufferedPipe<T> extends SinglePassPipe<T> {
    T getPoison();

    int getBufferSize();

    @Override
    default Iterable<T> iterable() {
        return () -> new BufferedIterator<>(this, getPoison(), getBufferSize());
    }
}
