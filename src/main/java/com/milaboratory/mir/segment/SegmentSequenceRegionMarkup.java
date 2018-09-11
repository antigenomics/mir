package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.util.Collection;
import java.util.EnumMap;

public class SegmentSequenceRegionMarkup<S extends Sequence<S>>
        extends PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType> {
    public SegmentSequenceRegionMarkup(
            EnumMap<AntigenReceptorRegionType, SequenceRegion<S, AntigenReceptorRegionType>> regionMap,
            S fullSequence) {
        super(fullSequence, regionMap, AntigenReceptorRegionType.class);
    }

    public SegmentSequenceRegionMarkup(Collection<SequenceRegion<S, AntigenReceptorRegionType>> sequenceRegions,
                                       S fullSequence) {
        super(fullSequence, sequenceRegions, AntigenReceptorRegionType.class);
    }
}
