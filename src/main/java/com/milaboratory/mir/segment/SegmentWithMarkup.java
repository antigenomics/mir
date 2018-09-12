package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public interface SegmentWithMarkup {
    int getReferencePoint();

    SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa();

    SequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt();
}
