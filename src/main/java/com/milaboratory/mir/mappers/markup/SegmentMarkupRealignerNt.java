package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapper;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentWithMarkup;

public final class SegmentMarkupRealignerNt<T extends SegmentWithMarkup>
        extends SegmentMarkupRealigner<T, NucleotideSequence> {
    public SegmentMarkupRealignerNt(SequenceMapper<T, NucleotideSequence> mapper,
                                    boolean requireReferencePointPresence) {
        super(mapper, SegmentWithMarkup::getRegionMarkupNt,
                requireReferencePointPresence);
    }

    public SegmentMarkupRealignerNt(Iterable<T> segments, SequenceMapperFactory<NucleotideSequence> mapperFactory,
                                    boolean requireReferencePointPresence) {
        super(mapperFactory.create(segments, Segment::getGermlineSequenceNt),
                SegmentWithMarkup::getRegionMarkupNt, requireReferencePointPresence);
    }
}
