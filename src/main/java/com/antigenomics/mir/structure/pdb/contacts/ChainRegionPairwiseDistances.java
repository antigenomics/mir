package com.antigenomics.mir.structure.pdb.contacts;

import com.antigenomics.mir.mappers.markup.SequenceRegion;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.structure.pdb.ChainRegion;

public final class ChainRegionPairwiseDistances<E1 extends Enum<E1>, E2 extends Enum<E2>>
        extends ChainPairwiseDistances<ChainRegion<E1>, ChainRegion<E2>> {

    public ChainRegionPairwiseDistances(ChainRegion<E1> chain1, ChainRegion<E2> chain2,
                                        float maxCaDistance, float maxAtomDistance) {
        super(chain1, chain2, maxCaDistance, maxAtomDistance);
    }

    public SequenceRegion<AminoAcidSequence, E1> getSequenceRegion1() {
        return chain1.getRegionInfo();
    }

    public SequenceRegion<AminoAcidSequence, E2> getSequenceRegion2() {
        return chain2.getRegionInfo();
    }
}
