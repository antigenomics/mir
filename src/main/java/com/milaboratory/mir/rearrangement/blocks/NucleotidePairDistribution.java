package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ConditionalDistribution1;

import java.util.Map;

public class NucleotidePairDistribution extends ConditionalDistribution1<Byte, Byte, NucleotideDistribution> {
    public NucleotidePairDistribution(Map<Byte, NucleotideDistribution> embeddedProbs) {
        super(embeddedProbs);
    }
}
