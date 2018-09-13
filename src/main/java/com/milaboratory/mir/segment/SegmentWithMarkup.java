package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public interface SegmentWithMarkup {
    int getReferencePoint();

    PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa();

    PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt();

    default SequenceRegion getRegion(SegmentRegionType segmentRegionType) {
        return (segmentRegionType.isAminoAcid() ?
                getRegionMarkupAa() : getRegionMarkupNt()).getRegion(segmentRegionType.getRegionType());
    }
}
