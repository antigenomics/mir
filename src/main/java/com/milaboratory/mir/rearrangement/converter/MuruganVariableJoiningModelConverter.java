package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;
import com.milaboratory.mir.rearrangement.parser.MuruganModelUtils;
import com.milaboratory.mir.segment.SegmentLibrary;

public class MuruganVariableJoiningModelConverter extends VariableJoiningModelConverter<MuruganModel> {
    private final MuruganConverterUtils.NucleotideDistributionBundle insertNucleotideProbs;

    public MuruganVariableJoiningModelConverter(SegmentLibrary segmentLibrary,
                                                MuruganModel muruganModel) {
        super(segmentLibrary, muruganModel);
        if (muruganModel.getGene().hasD()) {
            throw new IllegalArgumentException("Cannot convert Murugan model with D segment to a VariableJoiningModel.");
        }
        this.insertNucleotideProbs = MuruganConverterUtils.getNucleotideDistributionFromJoint(
                plainTextHierarchicalModel, MuruganModelUtils.VJMODEL_INS_JOINT_FORMULA
        );
    }

    @Override
    public VariableDistribution getVariableDistribution() {
        return getVariableDistribution(MuruganModelUtils.VJMODEL_V_FORMULA);
    }

    @Override
    public JoiningVariableDistribution getJoiningVariableDistribution() {
        return getJoiningVariableDistribution(MuruganModelUtils.VJMODEL_J_FORMULA);
    }

    @Override
    public InsertSizeDistribution getInsertSizeDistributionVJ() {
        return getInsertSizeDistribution(MuruganModelUtils.VJMODEL_INS_SZ_FORMULA);
    }

    @Override
    public VariableTrimmingDistribution getVariableTrimmingDistribution() {
        return getVariableTrimmingDistribution(MuruganModelUtils.VJMODEL_V_DEL_FORMULA);
    }

    @Override
    public JoiningTrimmingDistribution getJoiningTrimmingDistribution() {
        return getJoiningTrimmingDistribution(MuruganModelUtils.VJMODEL_J_DEL_FORMULA);
    }

    @Override
    public NucleotideDistribution getNucleotideDistributionVJ() {
        return insertNucleotideProbs.getNucleotideDistribution();
    }

    @Override
    public NucleotidePairDistribution getNucleotidePairDistributionVJ() {
        return insertNucleotideProbs.getNucleotidePairDistribution();
    }
}
