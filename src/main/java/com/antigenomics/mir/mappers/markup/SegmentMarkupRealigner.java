package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.mappers.SegmentMarkupProvider;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.SequenceProvider;
import com.antigenomics.mir.segment.SegmentWithMarkup;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.Sequence;

import java.util.Optional;

public class SegmentMarkupRealigner<T extends SegmentWithMarkup, S extends Sequence<S>>
        extends GenericMarkupRealigner<T, S, AntigenReceptorRegionType, PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>> {
    private final boolean requireReferencePointPresence;

    public SegmentMarkupRealigner(SequenceMapper<T, S> mapper,
                                  SegmentMarkupProvider<T, S> markupProvider,
                                  boolean requireReferencePointPresence,
                                  double minCoverage, int minMatches) {
        super(mapper, markupProvider, minCoverage, minMatches);
        this.requireReferencePointPresence = requireReferencePointPresence;
    }

    public SegmentMarkupRealigner(Iterable<T> segments,
                                  SequenceMapperFactory<S> mapperFactory,
                                  SequenceProvider<T, S> sequenceProvider,
                                  SegmentMarkupProvider<T, S> markupProvider,
                                  double minCoverage, int minMatches,
                                  boolean requireReferencePointPresence) {
        super(mapperFactory.create(segments, sequenceProvider), markupProvider, minCoverage, minMatches);
        this.requireReferencePointPresence = requireReferencePointPresence;
    }

    @Override
    public Optional<MarkupRealignmentResult<S, AntigenReceptorRegionType,
            PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>>> recomputeMarkup(S query) {
        var result = super.recomputeMarkup(query);
        if (requireReferencePointPresence &&
                result.isPresent() &&
                result.get().getMarkup().getRegion(AntigenReceptorRegionType.CDR3).isEmpty()) {
            return Optional.empty();
        }
        return result;
    }

    public boolean requireReferencePointPresence() {
        return requireReferencePointPresence;
    }
}
