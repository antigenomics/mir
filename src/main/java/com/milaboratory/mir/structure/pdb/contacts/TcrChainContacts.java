package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.*;
import com.milaboratory.mir.structure.pdb.ChainRegion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TcrChainContacts<E extends Enum<E>, C extends StructureChainWithMarkup<E>> {
    private final AntigenReceptorChain tcrChain;
    private final C otherChain;
    private final List<ChainRegionPairwiseDistances<AntigenReceptorRegionType, E>> contactsByRegion = new ArrayList<>();

    public TcrChainContacts(AntigenReceptorChain tcrChain, C otherChain) {
        this(tcrChain, otherChain, 25f, 5f, true);
    }

    public TcrChainContacts(AntigenReceptorChain tcrChain, C otherChain,
                            float maxCaDistance, float maxAtomDistance,
                            boolean allCdrPeptide) {
        this.tcrChain = tcrChain;
        this.otherChain = otherChain;
        for (ChainRegion<AntigenReceptorRegionType> tcrChainRegion : tcrChain.getRegions()) {
            for (ChainRegion<E> otherChainRegion : otherChain.getRegions()) {
                float mCad = maxCaDistance, mAd = maxAtomDistance;
                if (allCdrPeptide &&
                        tcrChainRegion.getRegionInfo().getRegionType().isCdr() &&
                        otherChainRegion.getRegionInfo().getRegionType() == PeptideRegionType.PEPTIDE) {
                    mCad = -1;
                    mAd = -1;
                }
                contactsByRegion.add(new ChainRegionPairwiseDistances<>(tcrChainRegion, otherChainRegion,
                        mCad, mAd));
            }
        }
    }

    public AntigenReceptorChain getTcrChain() {
        return tcrChain;
    }

    public C getOtherChain() {
        return otherChain;
    }

    public List<ChainRegionPairwiseDistances<AntigenReceptorRegionType, E>> getContactsByRegion() {
        return Collections.unmodifiableList(contactsByRegion);
    }
}
