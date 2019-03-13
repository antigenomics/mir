package com.antigenomics.mir.rearrangement;

import com.antigenomics.mir.rearrangement.blocks.NucleotideDistribution;
import com.antigenomics.mir.rearrangement.blocks.NucleotidePairDistribution;
import com.milaboratory.core.sequence.NucleotideSequence;

public class MarkovNucleotideSequenceDistribution
        extends MarkovSequenceDistribution<NucleotideSequence, NucleotideDistribution> {
    public MarkovNucleotideSequenceDistribution(NucleotideDistribution marginal,
                                                NucleotidePairDistribution joint,
                                                boolean reverse) {
        super(marginal, joint, reverse, NucleotideSequence.ALPHABET);
    }
}
