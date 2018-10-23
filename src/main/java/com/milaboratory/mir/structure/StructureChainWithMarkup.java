package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.pdb.Chain;

public interface StructureChainWithMarkup<E extends Enum<E>> {
    SequenceRegionMarkup<AminoAcidSequence, E, ? extends SequenceRegionMarkup> getMarkup();

    Chain getStructureChain();
}
