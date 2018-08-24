package com.milaboratory.mir.pipe;

public interface PipeFactory<T> {
    Pipe<T> getPipe();
}
