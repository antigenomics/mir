package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.TcrPeptideMhcComplex;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ComplexAnnotationWriter implements Consumer<TcrPeptideMhcComplex>, Closeable {
    private final List<TableWriter<TcrPeptideMhcComplex>> writers;

    public ComplexAnnotationWriter(List<TableWriter<TcrPeptideMhcComplex>> writers) {
        this.writers = writers;
    }

    @Override
    public void accept(TcrPeptideMhcComplex tcrPeptideMhcComplex) {
        writers.forEach(x -> x.accept(tcrPeptideMhcComplex));
    }

    @Override
    public void close() throws IOException {
        for (TableWriter<TcrPeptideMhcComplex> writer : writers) {
            writer.close();
        }
    }
}
