package com.antigenomics.mir.mappers;

import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.segment.SegmentWithMarkup;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.Sequence;

@FunctionalInterface
public interface SegmentMarkupProvider<T extends SegmentWithMarkup, S extends Sequence<S>> extends
        MarkupProvider<T, S, AntigenReceptorRegionType, PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>> {
}
