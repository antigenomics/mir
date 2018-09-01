package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;
import com.milaboratory.mir.segment.SegmentLibrary;

import static com.milaboratory.mir.rearrangement.parser.MuruganModeParserUtils.*;

public final class MuruganVJModelConverter extends VJModelConverter<MuruganModel> {
    private final MuruganConverterUtils.NucleotideDistributionBundle insertNucleotideProbs;
    private final VariableDistribution variableDistribution;
    private final JoiningVariableDistribution joiningVariableDistribution;
    private final InsertSizeDistribution insertSizeDistribution;
    private final VariableTrimmingDistribution variableTrimmingDistribution;
    private final JoiningTrimmingDistribution joiningTrimmingDistribution;

    public MuruganVJModelConverter(SegmentLibrary segmentLibrary,
                                   MuruganModel muruganModel) {
        super(segmentLibrary, muruganModel);
        if (muruganModel.getGene().hasD()) {
            throw new IllegalArgumentException("Cannot convert Murugan model with D segment to a VJ model.");
        }
        this.insertNucleotideProbs = MuruganConverterUtils.getNucleotideDistributionFromJoint(
                plainTextHierarchicalModel, VJ_INS_DINUCL_FORMULA
        );
        this.variableDistribution = getVariableDistribution(V_FORMULA);
        // safe get P(J|V)
        this.joiningVariableDistribution = getJoiningVariableDistribution(J_NAME, V_NAME);

        this.insertSizeDistribution = getInsertSizeDistribution(VJ_INS_SZ_FORMULA);
        this.variableTrimmingDistribution = getVariableTrimmingDistribution(V_DEL_FORMULA);
        this.joiningTrimmingDistribution = getJoiningTrimmingDistribution(J_DEL_FORMULA);
    }

    @Override
    public VariableDistribution getVariableDistribution() {
        return variableDistribution;
    }

    @Override
    public JoiningVariableDistribution getJoiningVariableDistribution() {
        return joiningVariableDistribution;
    }

    @Override
    public InsertSizeDistribution getInsertSizeDistributionVJ() {
        return insertSizeDistribution;
    }

    @Override
    public VariableTrimmingDistribution getVariableTrimmingDistribution() {
        return variableTrimmingDistribution;
    }

    @Override
    public JoiningTrimmingDistribution getJoiningTrimmingDistribution() {
        return joiningTrimmingDistribution;
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
