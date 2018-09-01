package com.milaboratory.mir.probability;

public class ByteDistribution extends Distribution<Byte> {
    public ByteDistribution(ByteDistribution toCopy) {
        super(toCopy);
    }

    public ByteDistribution(DistributionMap<Byte> distributionMap) {
        super(distributionMap);
    }

    public ByteDistribution(DistributionAccumulator<Byte> distributionAccumulator) {
        super(distributionAccumulator);
    }
}
