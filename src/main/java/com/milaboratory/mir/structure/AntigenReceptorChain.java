package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;

public interface AntigenReceptorChain {
    SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> getRegionMarkup();

    AntigenReceptorChainType getAntigenReceptorChainType();
}
