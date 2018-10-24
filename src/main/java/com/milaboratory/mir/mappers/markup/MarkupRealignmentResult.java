package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;

import java.util.Objects;

public class MarkupRealignmentResult<S extends Sequence<S>, E extends Enum<E>, M extends SequenceRegionMarkup<S, E, M>>
        implements Comparable<MarkupRealignmentResult<S, E, M>> {
    private final M markup;
    private final int numberOfMatches;
    private final double coverage;

    public MarkupRealignmentResult(M markup, int numberOfMatches, double coverage) {
        this.markup = markup;
        this.numberOfMatches = numberOfMatches;
        this.coverage = coverage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkupRealignmentResult<?, ?, ?> that = (MarkupRealignmentResult<?, ?, ?>) o;
        return numberOfMatches == that.numberOfMatches &&
                Double.compare(that.coverage, coverage) == 0 &&
                Objects.equals(markup, that.markup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markup, numberOfMatches, coverage);
    }

    @Override
    public int compareTo(MarkupRealignmentResult<S, E, M> o) {
        int res = Integer.compare(numberOfMatches, o.numberOfMatches);
        return res == 0 ? Double.compare(coverage, o.getCoverage()) : res;
    }
}
