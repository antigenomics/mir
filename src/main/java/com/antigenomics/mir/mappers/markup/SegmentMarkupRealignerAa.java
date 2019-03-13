package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.segment.Segment;
import com.antigenomics.mir.segment.SegmentWithMarkup;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;

public final class SegmentMarkupRealignerAa<T extends SegmentWithMarkup>
        extends SegmentMarkupRealigner<T, AminoAcidSequence> {
    public SegmentMarkupRealignerAa(SequenceMapper<T, AminoAcidSequence> mapper,
                                    double minCoverage, int minMatches,
                                    boolean requireReferencePointPresence) {
        super(mapper, SegmentWithMarkup::getRegionMarkupAa,
                requireReferencePointPresence,
                minCoverage, minMatches);
    }

    public SegmentMarkupRealignerAa(SequenceMapper<T, AminoAcidSequence> mapper) {
        this(mapper, 0.0, 3, true);
    }

    public SegmentMarkupRealignerAa(Iterable<T> segments,
                                    SequenceMapperFactory<AminoAcidSequence> mapperFactory,
                                    double minCoverage, int minMatches,
                                    boolean requireReferencePointPresence) {
        super(mapperFactory.create(segments, Segment::getGermlineSequenceAa),
                SegmentWithMarkup::getRegionMarkupAa,
                requireReferencePointPresence,
                minCoverage, minMatches);
    }

    public SegmentMarkupRealignerAa(Iterable<T> segments,
                                    SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this(segments, mapperFactory, 0.0, 3, true);
    }
}
