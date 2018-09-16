package com.milaboratory.mir.mappers;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.segment.SegmentWithMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

@FunctionalInterface
public interface SegmentMarkupProvider<T extends SegmentWithMarkup, S extends Sequence<S>> extends
        MarkupProvider<T, S, AntigenReceptorRegionType, PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>> {
}
