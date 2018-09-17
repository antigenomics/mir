package com.milaboratory.mir.structure.pdb.old;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.pdb.parser.RawAtom;

import java.util.List;

public class PdbChainRegion<E extends Enum<E>> extends OldPdbChain {
    private final OldPdbChain parent;
    private final SequenceRegion<AminoAcidSequence, E> region;

    public PdbChainRegion(List<RawAtom> atoms,
                          OldPdbChain parent,
                          SequenceRegion<AminoAcidSequence, E> region) {
        super(parent.getChainName(), atoms);
        this.parent = parent;
        this.region = region;
    }

    public PdbChainRegion(List<RawAtom> atoms,
                          OldPdbChain parent,
                          SequenceRegion<AminoAcidSequence, E> region,
                          boolean unsafe) {
        super(parent.getChainName(), atoms, unsafe);
        this.parent = parent;
        this.region = region;
    }

    public OldPdbChain getParent() {
        return parent;
    }

    public SequenceRegion<AminoAcidSequence, E> getRegion() {
        return region;
    }
}
