package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.structure.pdb.Chain;

public interface Peptide {
    AminoAcidSequence getSequence();

    Chain getChain();
}
