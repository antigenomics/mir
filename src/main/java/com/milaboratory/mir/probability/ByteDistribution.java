package com.milaboratory.mir.probability;

public class ByteDistribution extends Distribution<Byte> {
    public ByteDistribution(DistributionMap<Byte> distributionMap) {
        super(distributionMap, Byte.class);
    }

    public ByteDistribution(DistributionAccumulator<Byte> distributionAccumulator) {
        super(distributionAccumulator, Byte.class);
    }

    @Override
    public ByteDistribution copy() {
        return new ByteDistribution(getDistributionMap().copy());
    }
}
