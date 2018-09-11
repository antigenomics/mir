package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;


public class ClonotypeSequenceRegionMarkup<S extends Sequence<S>>
        extends ArrayBasedSequenceRegionMarkup<S, AntigenReceptorRegionType> {

    public ClonotypeSequenceRegionMarkup(S fullSequence, int[] markup) {
        super(fullSequence, markup, AntigenReceptorRegionType.class);
    }
}
