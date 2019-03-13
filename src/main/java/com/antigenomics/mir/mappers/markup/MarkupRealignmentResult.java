package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.Objects;

public class MarkupRealignmentResult<S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>>
        implements Comparable<MarkupRealignmentResult<S, E, M>> {
    private final Object payload; // todo: dirty, but too much generics otherwise/we'll use it internally
    private final M markup;
    private final int numberOfMatches;
    private final double coverage;

    public MarkupRealignmentResult(M markup, int numberOfMatches, double coverage) {
        this(markup, numberOfMatches, coverage, null);
    }

    public MarkupRealignmentResult(M markup, int numberOfMatches, double coverage, Object payload) {
        this.markup = markup;
        this.numberOfMatches = numberOfMatches;
        this.coverage = coverage;
        this.payload = payload;
    }

    public M getMarkup() {
        return markup;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public double getCoverage() {
        return coverage;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkupRealignmentResult<?, ?, ?> that = (MarkupRealignmentResult<?, ?, ?>) o;
        return numberOfMatches == that.numberOfMatches &&
                Double.compare(that.coverage, coverage) == 0 &&
                Objects.equals(payload, that.payload) &&
                Objects.equals(markup, that.markup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, markup, numberOfMatches, coverage);
    }

    @Override
    public int compareTo(MarkupRealignmentResult<S, E, M> o) {
        int res = Integer.compare(numberOfMatches, o.numberOfMatches);
        return res == 0 ? Double.compare(coverage, o.getCoverage()) : res;
    }
}
