package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapper;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentWithMarkup;

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
