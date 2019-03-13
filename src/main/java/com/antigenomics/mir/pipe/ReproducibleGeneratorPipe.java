package com.antigenomics.mir.pipe;

public final class ReproducibleGeneratorPipe<T> implements SinglePassPipe<T>, PipeFactory<T> {
    private final Generator<T> generator;
    private final long limit;
    private long counter = 0;

    public ReproducibleGeneratorPipe(Generator<T> generator,
                                     long limit) {
        this.generator = generator;
        this.limit = limit;
    }

    @Override
    public boolean hasNext() {
        return counter++ < limit;
    }

    @Override
    public T next() {
        return generator.generate();
    }

    @Override
    public Pipe<T> getPipe() {
        return new ReproducibleGeneratorPipe<>(generator, limit);
    }
}
