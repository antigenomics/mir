package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.blocks.NucleotideDistribution;
import com.milaboratory.mir.rearrangement.blocks.NucleotidePairDistribution;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.*;
import static com.milaboratory.mir.rearrangement.converter.ConverterUtils.*;

public final class MuruganConverterUtils {
    private MuruganConverterUtils() {

    }

    public static NucleotideDistributionBundle getNucleotideDistributionFromJoint(MuruganModel muruganModel,
                                                                                  String blockName) {
        var marginalConditional = decomposeJoint(muruganModel.getProbabilityMap(blockName));

        return new NucleotideDistributionBundle(
                NucleotideDistribution.fromMap(convertNucleotide(marginalConditional.getMarginal())),
                NucleotidePairDistribution.fromMap(convertNucleotidePair(marginalConditional.getConditional()))
        );
    }

    public static class NucleotideDistributionBundle {
        private final NucleotideDistribution nucleotideDistribution;
        private final NucleotidePairDistribution nucleotidePairDistribution;

        public NucleotideDistributionBundle(NucleotideDistribution nucleotideDistribution,
                                            NucleotidePairDistribution nucleotidePairDistribution) {
            this.nucleotideDistribution = nucleotideDistribution;
            this.nucleotidePairDistribution = nucleotidePairDistribution;
        }

        public NucleotideDistribution getNucleotideDistribution() {
            return nucleotideDistribution;
        }

        public NucleotidePairDistribution getNucleotidePairDistribution() {
            return nucleotidePairDistribution;
        }
    }
}
