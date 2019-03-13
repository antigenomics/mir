package com.antigenomics.mir.rearrangement.converter;

import com.antigenomics.mir.probability.parser.PlainTextHierarchicalModel;
import com.antigenomics.mir.rearrangement.VariableDiversityJoiningModel;
import com.antigenomics.mir.rearrangement.blocks.*;
import com.antigenomics.mir.rearrangement.blocks.*;
import com.antigenomics.mir.segment.SegmentLibrary;

public abstract class VDJModelConverter<T extends PlainTextHierarchicalModel>
        extends Converter<T, VariableDiversityJoiningModel> {
    public VDJModelConverter(T plainTextHierarchicalModel, SegmentLibrary segmentLibrary) {
        super(plainTextHierarchicalModel, segmentLibrary);
    }

    public abstract VariableDistribution getVariableDistribution();

    public abstract JoiningVariableDistribution getJoiningVariableDistribution();

    public abstract DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution();

    public abstract InsertSizeDistribution getInsertSizeDistributionVD();

    public abstract InsertSizeDistribution getInsertSizeDistributionDJ();

    public abstract VariableTrimmingDistribution getVariableTrimmingDistribution();

    public abstract JoiningTrimmingDistribution getJoiningTrimmingDistribution();

    public abstract DiversityTrimming5Distribution getDiversityTrimming5Distribution();

    public abstract DiversityTrimming3Distribution getDiversityTrimming3Distribution();

    public abstract NucleotideDistribution getNucleotideDistributionVD();

    public abstract NucleotideDistribution getNucleotideDistributionDJ();

    public abstract NucleotidePairDistribution getNucleotidePairDistributionVD();

    public abstract NucleotidePairDistribution getNucleotidePairDistributionDJ();

    @Override
    public VariableDiversityJoiningModel getRearrangementModel() {
        return new VariableDiversityJoiningModel(
                getVariableDistribution(),
                getJoiningVariableDistribution(),
                getDiversityJoiningVariableDistribution(),
                getJoiningTrimmingDistribution(), getVariableTrimmingDistribution(),
                getDiversityTrimming5Distribution(), getDiversityTrimming3Distribution(),
                getInsertSizeDistributionVD(), getInsertSizeDistributionDJ(),
                getNucleotideDistributionVD(), getNucleotideDistributionDJ(),
                getNucleotidePairDistributionVD(), getNucleotidePairDistributionDJ()
        );
    }
}
