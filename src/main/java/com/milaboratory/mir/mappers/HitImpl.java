package com.milaboratory.mir.mappers;

import java.util.Objects;

public final class HitImpl<Q, T> implements Hit<Q, T> {
    private final Q query;
    private final T target;
    private final float score;

    public HitImpl(Q query, T target, float score) {
        this.query = query;
        this.target = target;
        this.score = score;
    }

    @Override
    public Q getQuery() {
        return query;
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
        HitImpl<?, ?> hit = (HitImpl<?, ?>) o;
        return Float.compare(hit.score, score) == 0 &&
                Objects.equals(query, hit.query) &&
                Objects.equals(target, hit.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, target, score);
    }
}
