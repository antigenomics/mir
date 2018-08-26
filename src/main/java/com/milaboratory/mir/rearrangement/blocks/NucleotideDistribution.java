package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ByteDistribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;

import java.util.Map;

public class NucleotideDistribution extends ByteDistribution {
    public NucleotideDistribution(DistributionMap<Byte> distributionMap) {
        super(distributionMap);
    }

    public NucleotideDistribution(DistributionAccumulator<Byte> distributionAccumulator) {
        super(distributionAccumulator);
    }

    public static NucleotideDistribution fromMap(Map<Byte, Double> probabilities) {
        return new NucleotideDistribution(new DistributionMap<>(probabilities));
    }
}
