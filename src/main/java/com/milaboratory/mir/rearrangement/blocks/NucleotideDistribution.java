package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.probability.ByteDistribution;
import com.milaboratory.mir.probability.DistributionAccumulator;
import com.milaboratory.mir.probability.DistributionMap;

import java.util.Random;

public class NucleotideDistribution extends ByteDistribution {
    public NucleotideDistribution(DistributionMap<Byte> distributionMap, Random random) {
        super(distributionMap, random);
    }

    public NucleotideDistribution(DistributionAccumulator<Byte> distributionAccumulator, Random random) {
        super(distributionAccumulator, random);
    }
}
