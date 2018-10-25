package com.milaboratory.mir;

import java.io.*;
import java.util.function.Consumer;

public abstract class TableWriter<T> implements Consumer<T>, Closeable {
    private final PrintWriter pw;

    public TableWriter(OutputStream os, String header) {
        this.pw = new PrintWriter(os);
        pw.println(header);
    }


    // todo: smart file creation with full path, overwrite protection
    public TableWriter(String fileName, String header) throws FileNotFoundException {
        this(new FileOutputStream(fileName), header);
    }

    protected abstract String convert(T obj);

    public void accept(T obj) {
        pw.println(convert(obj));
    }

    @Override
    public void close() throws IOException {
        pw.close();
    }
}
