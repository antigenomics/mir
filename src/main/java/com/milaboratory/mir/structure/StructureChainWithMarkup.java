package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.ChainRegion;

import java.util.List;
import java.util.stream.Collectors;

public interface StructureChainWithMarkup<E extends Enum<E>> {
    SequenceRegionMarkup<AminoAcidSequence, E, ? extends SequenceRegionMarkup> getMarkup();

    Chain getStructureChain();

    String getChainTypeStr();

    default String getStructureChainId() {
        return ((Character) getStructureChain().getChainIdentifier()).toString();
    }

    default String getAlleleInfoStr() {
        return "NA";
    }

    default List<ChainRegion<E>> getRegions() {
        return getMarkup().getAllRegions().values().stream()
                .map(x -> getStructureChain().extractRegion(x))
                .collect(Collectors.toList());
    }
}
