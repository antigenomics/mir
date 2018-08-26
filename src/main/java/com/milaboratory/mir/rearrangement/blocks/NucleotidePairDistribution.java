package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.probability.DistributionMap;

import java.util.Map;

public class NucleotidePairDistribution extends ConditionalDistribution1<Byte, Byte, NucleotideDistribution> {
    public NucleotidePairDistribution(Map<Byte, NucleotideDistribution> embeddedProbs) {
        super(embeddedProbs);
    }

    public NucleotidePairDistribution fromMap(Map<Byte, Map<Byte, Double>> probabilities) {
        return new NucleotidePairDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> NucleotideDistribution.fromMap(e.getValue())
        ));
    }
}
