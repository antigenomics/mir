package com.antigenomics.mir.rearrangement.blocks;

import com.antigenomics.mir.probability.ByteDistribution;
import com.antigenomics.mir.probability.DistributionMap;

import java.util.Map;

public class NucleotideDistribution
        extends ByteDistribution
        implements ModelBlock<NucleotideDistribution> {
    NucleotideDistribution(ByteDistribution toCopy, boolean fromAccumulator) {
        super(toCopy, fromAccumulator);
    }

    private NucleotideDistribution(DistributionMap<Byte> distributionMap) {
        super(distributionMap);
    }

    public static NucleotideDistribution fromMap(Map<Byte, Double> probabilities) {
        return new NucleotideDistribution(new DistributionMap<>(probabilities));
    }

    @Override
    public NucleotideDistribution copy(boolean fromAccumulator) {
        return new NucleotideDistribution(this, fromAccumulator);
    }
}
