package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegion;

import java.util.List;

public class PdbChainRegion<E extends Enum<E>> extends PdbChain {
    private final PdbChain parent;
    private final SequenceRegion<AminoAcidSequence, E> region;

    public PdbChainRegion(List<Atom> atoms,
                          PdbChain parent,
                          SequenceRegion<AminoAcidSequence, E> region) {
        super(parent.getChainName(), atoms);
        this.parent = parent;
        this.region = region;
    }

    public PdbChainRegion(List<Atom> atoms,
                          PdbChain parent,
                          SequenceRegion<AminoAcidSequence, E> region,
                          boolean unsafe) {
        super(parent.getChainName(), atoms, unsafe);
        this.parent = parent;
        this.region = region;
    }

    public PdbChain getParent() {
        return parent;
    }

    public SequenceRegion<AminoAcidSequence, E> getRegion() {
        return region;
    }
}
