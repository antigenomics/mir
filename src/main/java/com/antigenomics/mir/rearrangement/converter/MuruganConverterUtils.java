package com.antigenomics.mir.rearrangement.converter;

import com.antigenomics.mir.rearrangement.RearrangementModel;
import com.antigenomics.mir.rearrangement.blocks.NucleotideDistribution;
import com.antigenomics.mir.rearrangement.blocks.NucleotidePairDistribution;
import com.antigenomics.mir.rearrangement.parser.MuruganModel;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.segment.parser.FastaSegmentLibraryUtils;

import static com.antigenomics.mir.probability.parser.PlainTextHierarchicalModelUtils.*;

public final class MuruganConverterUtils {
    private MuruganConverterUtils() {

    }

    public static NucleotideDistributionBundle getNucleotideDistributionFromJoint(MuruganModel muruganModel,
                                                                                  String blockName) {
        var marginalConditional = decomposeJoint(muruganModel.getProbabilityMap(blockName));

        return new NucleotideDistributionBundle(
                NucleotideDistribution.fromMap(ConverterUtils.convertNucleotide(marginalConditional.getMarginal())),
                NucleotidePairDistribution.fromMap(ConverterUtils.convertNucleotidePair(marginalConditional.getConditional()))
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

    public static Converter getConverter(MuruganModel muruganModel,
                                         SegmentLibrary segmentLibrary) {
        if (muruganModel.getGene().hasD()) {
            return new MuruganVDJModelConverter(muruganModel, segmentLibrary);
        } else {
            return new MuruganVJModelConverter(muruganModel, segmentLibrary);
        }
    }

    public static Converter getConverter(MuruganModel muruganModel) {
        return getConverter(muruganModel,
                FastaSegmentLibraryUtils.loadMurugan(muruganModel.getSpecies(), muruganModel.getGene()));
    }

    public static RearrangementModel asRearrangementModel(MuruganModel muruganModel,
                                                          SegmentLibrary segmentLibrary) {
        return getConverter(muruganModel, segmentLibrary).getRearrangementModel();
    }

    public static RearrangementModel asRearrangementModel(MuruganModel muruganModel) {
        return asRearrangementModel(muruganModel,
                FastaSegmentLibraryUtils.loadMurugan(muruganModel.getSpecies(), muruganModel.getGene()));
    }
}
