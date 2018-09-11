package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.clonotype.structure.AntigenReceptorRegionMarkup;

public interface AntigenReceptorChain {
    AminoAcidSequence getFullSequence();

    AntigenReceptorRegionMarkup<AminoAcidSequence> getRegionMarkup();
}
