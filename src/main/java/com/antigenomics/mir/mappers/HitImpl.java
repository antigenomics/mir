package com.antigenomics.mir.mappers;

import java.util.Objects;

public final class HitImpl<T> implements Hit<T> {
    private final T target;
    private final float score;

    public HitImpl(T target, float score) {
        this.target = target;
        this.score = score;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitImpl<?> hit = (HitImpl<?>) o;
        return Float.compare(hit.score, score) == 0 &&
                Objects.equals(target, hit.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, score);
    }
}
