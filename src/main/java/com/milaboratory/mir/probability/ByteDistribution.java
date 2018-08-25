package com.milaboratory.mir.probability;

import java.util.Random;

public class ByteDistribution extends Distribution<Byte> {
    public ByteDistribution(DistributionMap<Byte> distributionMap, Random random) {
        super(distributionMap, Byte.class, random);
    }

    public ByteDistribution(DistributionAccumulator<Byte> distributionAccumulator, Random random) {
        super(distributionAccumulator, Byte.class, random);
    }
}
