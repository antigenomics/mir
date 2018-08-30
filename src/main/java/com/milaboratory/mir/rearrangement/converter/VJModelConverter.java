package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModel;
import com.milaboratory.mir.rearrangement.VariableJoiningModel;
import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.SegmentLibrary;

public abstract class VJModelConverter<T extends PlainTextHierarchicalModel>
        extends Converter<T, VariableJoiningModel> {
    public VJModelConverter(SegmentLibrary segmentLibrary, T plainTextHierarchicalModel) {
        super(segmentLibrary, plainTextHierarchicalModel);
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
