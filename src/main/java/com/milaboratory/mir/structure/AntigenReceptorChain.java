package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;

public interface AntigenReceptorChain {
    AminoAcidSequence getFullSequence();

    SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkup();
}
