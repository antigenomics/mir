package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.segment.Segment;
import com.antigenomics.mir.segment.SegmentWithMarkup;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;

public final class SegmentMarkupRealignerNt<T extends SegmentWithMarkup>
        extends SegmentMarkupRealigner<T, NucleotideSequence> {

    public SegmentMarkupRealignerNt(SequenceMapper<T, NucleotideSequence> mapper,
                                    double minCoverage, int minMatches,
                                    boolean requireReferencePointPresence) {
        super(mapper, SegmentWithMarkup::getRegionMarkupNt,
                requireReferencePointPresence,
                minCoverage, minMatches);
    }

    public SegmentMarkupRealignerNt(SequenceMapper<T, NucleotideSequence> mapper) {
        this(mapper, 0.0, 9, true);
    }

    public SegmentMarkupRealignerNt(Iterable<T> segments,
                                    SequenceMapperFactory<NucleotideSequence> mapperFactory,
                                    double minCoverage, int minMatches,
                                    boolean requireReferencePointPresence) {
        super(mapperFactory.create(segments, Segment::getGermlineSequenceNt),
                SegmentWithMarkup::getRegionMarkupNt,
                requireReferencePointPresence,
                minCoverage, minMatches);
    }

    public SegmentMarkupRealignerNt(Iterable<T> segments,
                                    SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        this(segments, mapperFactory, 0.0, 9, true);
    }
}
