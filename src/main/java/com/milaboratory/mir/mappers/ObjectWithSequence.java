package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;

import java.util.Objects;

public final class ObjectWithSequence<T, S extends Sequence<S>> {
    private final T object;
    private final S sequence;

    public static <T, S extends Sequence<S>> ObjectWithSequence<T, S> wrap(T object,
                                                                           SequenceProvider<T, S> provider) {
        return new ObjectWithSequence<>(object, provider.getSequence(object));
    }

    public ObjectWithSequence(T object, S sequence) {
        this.object = object;
        this.sequence = sequence;
    }

    public T getObject() {
        return object;
    }

    public S getSequence() {
        return sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectWithSequence<?, ?> that = (ObjectWithSequence<?, ?>) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, sequence);
    }
}
