package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.segment.ConstantSegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.pdb.Chain;

public interface AntigenReceptorChain {
    SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> getRegionMarkup();

    AntigenReceptorChainType getAntigenReceptorChainType();

    VariableSegment getVariableSegment();

    JoiningSegment getJoiningSegment();

    ConstantSegment getConstantSegment();

    Chain getChain();
}
