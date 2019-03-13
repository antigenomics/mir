package com.antigenomics.mir.structure;

import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.structure.pdb.Chain;
import com.antigenomics.mir.structure.pdb.ChainRegion;
import com.milaboratory.core.sequence.AminoAcidSequence;

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
