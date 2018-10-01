package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.RearrangementModel;
import com.milaboratory.mir.rearrangement.VariableDiversityJoiningModel;
import com.milaboratory.mir.rearrangement.blocks.NucleotideDistribution;
import com.milaboratory.mir.rearrangement.blocks.NucleotidePairDistribution;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;
import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.parser.FastaSegmentLibraryUtils;

import java.util.Map;
import java.util.stream.Collectors;

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
