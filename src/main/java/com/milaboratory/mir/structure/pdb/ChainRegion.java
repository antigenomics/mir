package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegion;

import java.util.List;

public final class ChainRegion<E extends Enum<E>> extends Chain {
    private final Chain parent;
    private final SequenceRegion<AminoAcidSequence, E> region;

    ChainRegion(Chain parent, List<Residue> newResidues, SequenceRegion<AminoAcidSequence, E> region) {
        super(parent, newResidues);
        this.parent = parent;
        this.region = region;

        if (!region.getSequence().equals(this.getSequence())) {
            throw new IllegalArgumentException("Range sequence is '" + region.getSequence() +
                    "' while sequence from PDB is '" + this.getSequence() + "'");
        }
        if (!getOriginalRange().equals(region.asRange())) {
            throw new IllegalArgumentException("Range mismatch");
        }
    }

    public Chain getParent() {
        return parent;
    }

    public SequenceRegion<AminoAcidSequence, E> getRegionInfo() {
        return region;
    }
}
