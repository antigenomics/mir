package com.antigenomics.mir.segment;

import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegion;

public interface SegmentWithMarkup extends Segment {
    int getReferencePoint();

    PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa();

    PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt();

    default SequenceRegion getRegion(SegmentRegionType segmentRegionType) {
        return (segmentRegionType.isAminoAcid() ?
                getRegionMarkupAa() : getRegionMarkupNt()).getRegion(segmentRegionType.getRegionType());
    }
}
