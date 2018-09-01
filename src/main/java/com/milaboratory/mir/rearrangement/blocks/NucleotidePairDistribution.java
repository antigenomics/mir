package com.milaboratory.mir.rearrangement.blocks;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.probability.ConditionalDistribution1;
import com.milaboratory.mir.probability.DistributionFactory;
import com.milaboratory.mir.probability.DistributionMap;

import java.util.Map;

public class NucleotidePairDistribution
        extends ConditionalDistribution1<Byte, Byte, NucleotideDistribution>
        implements ModelBlock<NucleotidePairDistribution> {
    NucleotidePairDistribution(ConditionalDistribution1<Byte, Byte, NucleotideDistribution> toCopy, boolean fromAccumulator) {
        super(toCopy, NucleotideDistribution::new, fromAccumulator);
    }

    private NucleotidePairDistribution(Map<Byte, NucleotideDistribution> probabilityMap) {
        super(probabilityMap, NucleotideDistribution::new, true);
    }

    public static NucleotidePairDistribution fromMap(Map<Byte, Map<Byte, Double>> probabilities) {
        return new NucleotidePairDistribution(CommonUtils.map2map(
                probabilities,
                Map.Entry::getKey,
                e -> NucleotideDistribution.fromMap(e.getValue())
        ));
    }

    @Override
    public NucleotidePairDistribution copy(boolean fromAccumulator) {
        return new NucleotidePairDistribution(this, fromAccumulator);
    }
}
