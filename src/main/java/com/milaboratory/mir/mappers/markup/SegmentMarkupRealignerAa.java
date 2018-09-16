package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapper;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentWithMarkup;

public final class SegmentMarkupRealignerAa<T extends SegmentWithMarkup>
        extends SegmentMarkupRealigner<T, AminoAcidSequence> {
    public SegmentMarkupRealignerAa(SequenceMapper<T, AminoAcidSequence> mapper,
                                    boolean requireReferencePointPresence) {
        super(mapper, SegmentWithMarkup::getRegionMarkupAa, requireReferencePointPresence);
    }

    public SegmentMarkupRealignerAa(Iterable<T> segments, SequenceMapperFactory<AminoAcidSequence> mapperFactory,
                                    boolean requireReferencePointPresence) {
        super(mapperFactory.create(segments, Segment::getGermlineSequenceAa), SegmentWithMarkup::getRegionMarkupAa,
                requireReferencePointPresence);
    }
}
