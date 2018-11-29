package com.milaboratory.mir.structure;

import com.milaboratory.mir.structure.pdb.ChainRegion;

import java.util.ArrayList;
import java.util.List;

public interface HeterodimerComplex<E extends Enum<E>, C extends StructureChainWithMarkup<E>> {
    C getFirstChain();

    C getSecondChain();

    default List<ChainRegion> getRegions() {
        var regions = new ArrayList<ChainRegion>();

        regions.addAll(getFirstChain().getRegions());
        regions.addAll(getSecondChain().getRegions());

        return regions;
    }
}
