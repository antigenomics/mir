package com.antigenomics.mir.pipe;

public interface PipeFactory<T> {
    Pipe<T> getPipe();
}
