package com.antigenomics.mir;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class MultiTableWriter<T> implements Consumer<T>, Closeable {
    private final List<TableWriter<T>> writers;

    public MultiTableWriter(List<TableWriter<T>> writers) {
        this.writers = writers;
    }

    @Override
    public void accept(T obj) {
        writers.forEach(x -> x.accept(obj));
    }

    @Override
    public void close() throws IOException {
        for (TableWriter<T> writer : writers) {
            writer.close();
        }
    }
}
