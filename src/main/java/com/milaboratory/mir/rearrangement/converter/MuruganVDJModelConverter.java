package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;
import com.milaboratory.mir.rearrangement.parser.MuruganModeParserlUtils;
import com.milaboratory.mir.segment.SegmentLibrary;

import static com.milaboratory.mir.rearrangement.parser.MuruganModeParserlUtils.*;

public final class MuruganVDJModelConverter extends VDJModelConverter<MuruganModel> {
    private final MuruganConverterUtils.NucleotideDistributionBundle vdInsertNucleotideProbs,
            djInsertNucleotideProbs;
    private final VariableDistribution variableDistribution;
    private final JoiningVariableDistribution joiningVariableDistribution;
    private final DiversityJoiningVariableDistribution diversityJoiningVariableDistribution;
    private final VariableTrimmingDistribution variableTrimmingDistribution;
    private final JoiningTrimmingDistribution joiningTrimmingDistribution;
    private final InsertSizeDistribution vdInsertSizeDistribution,
            djInsertSizeDistribution;
    private final DiversityTrimming5Distribution diversityTrimming5Distribution;
    private final DiversityTrimming3Distribution diversityTrimming3Distribution;

    public MuruganVDJModelConverter(SegmentLibrary segmentLibrary,
                                    MuruganModel muruganModel) {
        super(segmentLibrary, muruganModel);
        if (!muruganModel.getGene().hasD()) {
            throw new IllegalArgumentException("Cannot convert Murugan model without D segment to a VDJ model.");
        }
        this.vdInsertNucleotideProbs = MuruganConverterUtils.getNucleotideDistributionFromJoint(
                plainTextHierarchicalModel, MuruganModeParserlUtils.VD_INS_DINUCL_FORMULA
        );
        this.djInsertNucleotideProbs = MuruganConverterUtils.getNucleotideDistributionFromJoint(
                plainTextHierarchicalModel, MuruganModeParserlUtils.DJ_INS_DINUCL_FORMULA
        );
        this.variableDistribution = getVariableDistribution(MuruganModeParserlUtils.V_FORMULA);
        // safe get P(J|V)
        this.joiningVariableDistribution = getJoiningVariableDistribution(J_NAME, V_NAME);
        // safe get P(D|J,V)
        this.diversityJoiningVariableDistribution = getDiversityJoiningVariableDistribution(D_NAME, J_NAME, V_NAME);


        this.vdInsertSizeDistribution = getInsertSizeDistribution(VD_INS_SZ_FORMULA);
        this.djInsertSizeDistribution = getInsertSizeDistribution(DJ_INS_SZ_FORMULA);

        this.variableTrimmingDistribution = getVariableTrimmingDistribution(V_DEL_FORMULA);
        this.joiningTrimmingDistribution = getJoiningTrimmingDistribution(J_DEL_FORMULA);

        this.diversityTrimming5Distribution = getDiversityTrimming5Distribution(D_DEL5_FORMULA);
        this.diversityTrimming3Distribution = getDiversityTrimming3Distribution(D_DEL3_FORMULA);
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
    public DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution() {
        return diversityJoiningVariableDistribution;
    }

    @Override
    public InsertSizeDistribution getInsertSizeDistributionVD() {
        return vdInsertSizeDistribution;
    }

    @Override
    public InsertSizeDistribution getInsertSizeDistributionDJ() {
        return djInsertSizeDistribution;
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
    public DiversityTrimming5Distribution getDiversityTrimming5Distribution() {
        return diversityTrimming5Distribution;
    }

    @Override
    public DiversityTrimming3Distribution getDiversityTrimming3Distribution() {
        return diversityTrimming3Distribution;
    }

    @Override
    public NucleotideDistribution getNucleotideDistributionVD() {
        return vdInsertNucleotideProbs.getNucleotideDistribution();
    }

    @Override
    public NucleotideDistribution getNucleotideDistributionDJ() {
        return djInsertNucleotideProbs.getNucleotideDistribution();
    }

    @Override
    public NucleotidePairDistribution getNucleotidePairDistributionVD() {
        return vdInsertNucleotideProbs.getNucleotidePairDistribution();
    }

    @Override
    public NucleotidePairDistribution getNucleotidePairDistributionDJ() {
        return djInsertNucleotideProbs.getNucleotidePairDistribution();
    }
}
