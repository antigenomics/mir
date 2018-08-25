package com.milaboratory.mir.rearrangement;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.rearrangement.blocks.NucleotideDistribution;
import com.milaboratory.mir.rearrangement.blocks.NucleotidePairDistribution;

public class MarkovNucleotideSequenceDistribution
        extends MarkovSequenceDistribution<NucleotideSequence, NucleotideDistribution> {
    public MarkovNucleotideSequenceDistribution(NucleotideDistribution marginal,
                                                NucleotidePairDistribution joint,
                                                boolean reverse) {
        super(marginal, joint, reverse, NucleotideSequence.ALPHABET);
    }
}
