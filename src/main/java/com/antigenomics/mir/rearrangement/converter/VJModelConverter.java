package com.antigenomics.mir.rearrangement.converter;

import com.antigenomics.mir.probability.parser.PlainTextHierarchicalModel;
import com.antigenomics.mir.rearrangement.VariableJoiningModel;
import com.antigenomics.mir.rearrangement.blocks.*;
import com.antigenomics.mir.rearrangement.blocks.*;
import com.antigenomics.mir.segment.SegmentLibrary;

public abstract class VJModelConverter<T extends PlainTextHierarchicalModel>
        extends Converter<T, VariableJoiningModel> {
    public VJModelConverter(T plainTextHierarchicalModel, SegmentLibrary segmentLibrary) {
        super(plainTextHierarchicalModel, segmentLibrary);
    }

    public abstract VariableDistribution getVariableDistribution();

    public abstract JoiningVariableDistribution getJoiningVariableDistribution();

    public abstract InsertSizeDistribution getInsertSizeDistributionVJ();

    public abstract VariableTrimmingDistribution getVariableTrimmingDistribution();

    public abstract JoiningTrimmingDistribution getJoiningTrimmingDistribution();

    public abstract NucleotideDistribution getNucleotideDistributionVJ();

    public abstract NucleotidePairDistribution getNucleotidePairDistributionVJ();

    @Override
    public VariableJoiningModel getRearrangementModel() {
        return new VariableJoiningModel(
                getVariableDistribution(), getJoiningVariableDistribution(),
                getJoiningTrimmingDistribution(), getVariableTrimmingDistribution(),
                getInsertSizeDistributionVJ(),
                getNucleotideDistributionVJ(), getNucleotidePairDistributionVJ()
        );
    }
}
