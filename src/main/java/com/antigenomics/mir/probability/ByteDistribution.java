package com.antigenomics.mir.probability;

public class ByteDistribution extends Distribution<Byte> {
    public ByteDistribution(ByteDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    public ByteDistribution(DistributionMap<Byte> distributionMap) {
        super(distributionMap);
    }
}
