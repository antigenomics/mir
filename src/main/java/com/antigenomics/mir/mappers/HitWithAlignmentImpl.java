package com.antigenomics.mir.mappers;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

import java.util.Objects;

public final class HitWithAlignmentImpl< T, S extends Sequence<S>> implements HitWithAlignment< T, S> {
    private final T target;
    private final Alignment<S> alignment;

    public HitWithAlignmentImpl(T target, Alignment<S> alignment) {
        this.target = target;
        this.alignment = alignment;
    }

    public Alignment<S> getAlignment() {
        return alignment;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public float getScore() {
        return alignment.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitWithAlignmentImpl<?, ?> that = (HitWithAlignmentImpl<?, ?>) o;
        return Objects.equals(target, that.target) &&
                Objects.equals(alignment, that.alignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, alignment);
    }
}
