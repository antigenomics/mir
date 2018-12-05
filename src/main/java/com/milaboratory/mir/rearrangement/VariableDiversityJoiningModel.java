package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableDiversityJoiningModel implements RearrangementModel {
    private final VariableDistribution variableSegmentDistribution;
    private final JoiningVariableDistribution joiningVariableDistribution;
    private final DiversityJoiningVariableDistribution diversityJoiningVariableDistribution;
    private final JoiningTrimmingDistribution joiningTrimmingDistribution;
    private final VariableTrimmingDistribution variableTrimmingDistribution;
    private final DiversityTrimming5Distribution diversityTrimming5Distribution;
    private final DiversityTrimming3Distribution diversityTrimming3Distribution;
    private final InsertSizeDistribution insertSizeDistributionVD,
            insertSizeDistributionDJ;
    private final NucleotideDistribution nucleotideDistributionVD,
            nucleotideDistributionDJ;
    private final NucleotidePairDistribution nucleotidePairDistributionVD,
            nucleotidePairDistributionDJ;
    private final MarkovNucleotideSequenceDistribution markovSequenceDistributionVD,
            markovSequenceDistributionDJ;

    public VariableDiversityJoiningModel(VariableDistribution variableSegmentDistribution,
                                         JoiningVariableDistribution joiningVariableDistribution,
                                         DiversityJoiningVariableDistribution diversityJoiningVariableDistribution,
                                         JoiningTrimmingDistribution joiningTrimmingDistribution,
                                         VariableTrimmingDistribution variableTrimmingDistribution,
                                         DiversityTrimming5Distribution diversityTrimming5Distribution,
                                         DiversityTrimming3Distribution diversityTrimming3Distribution,
                                         InsertSizeDistribution insertSizeDistributionVD,
                                         InsertSizeDistribution insertSizeDistributionDJ,
                                         NucleotideDistribution nucleotideDistributionVD,
                                         NucleotideDistribution nucleotideDistributionDJ,
                                         NucleotidePairDistribution nucleotidePairDistributionVD,
                                         NucleotidePairDistribution nucleotidePairDistributionDJ) {
        this.variableSegmentDistribution = variableSegmentDistribution;
        this.joiningVariableDistribution = joiningVariableDistribution;
        this.diversityJoiningVariableDistribution = diversityJoiningVariableDistribution;
        this.joiningTrimmingDistribution = joiningTrimmingDistribution;
        this.variableTrimmingDistribution = variableTrimmingDistribution;
        this.diversityTrimming5Distribution = diversityTrimming5Distribution;
        this.diversityTrimming3Distribution = diversityTrimming3Distribution;
        this.insertSizeDistributionVD = insertSizeDistributionVD;
        this.insertSizeDistributionDJ = insertSizeDistributionDJ;
        this.nucleotideDistributionVD = nucleotideDistributionVD;
        this.nucleotideDistributionDJ = nucleotideDistributionDJ;
        this.nucleotidePairDistributionVD = nucleotidePairDistributionVD;
        this.nucleotidePairDistributionDJ = nucleotidePairDistributionDJ;
        this.markovSequenceDistributionVD = new MarkovNucleotideSequenceDistribution(nucleotideDistributionVD,
                nucleotidePairDistributionVD, false);
        this.markovSequenceDistributionDJ = new MarkovNucleotideSequenceDistribution(nucleotideDistributionDJ,
                nucleotidePairDistributionDJ, true);
    }

    @Override
    public boolean hasD() {
        return true;
    }

    @Override
    public RearrangementTemplate generate() {
        VariableSegment variableSegment = variableSegmentDistribution.getDistributionSampler().generate();
        JoiningSegment joiningSegment = joiningVariableDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();

        DiversitySegment diversitySegment = diversityJoiningVariableDistribution
                .getDistribution0(variableSegment, joiningSegment)
                .getDistributionSampler().generate();

        int variableTrimming = variableTrimmingDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int joiningTrimming = joiningTrimmingDistribution.getDistribution0(joiningSegment)
                .getDistributionSampler().generate();
        int diversitySegmentTrimming5 = diversityTrimming5Distribution.getDistribution0(diversitySegment)
                .getDistributionSampler().generate();
        int diversitySegmentTrimming3 = diversityTrimming3Distribution
                .getDistribution0(diversitySegment, diversitySegmentTrimming5)
                .getDistributionSampler().generate();

        int vdInsertSize = insertSizeDistributionVD.getDistributionSampler().generate();
        int djInsertSize = insertSizeDistributionDJ.getDistributionSampler().generate();

        return new RearrangementTemplate(
                variableSegment,
                joiningSegment,
                diversitySegment,
                variableTrimming,
                joiningTrimming,
                diversitySegmentTrimming5,
                diversitySegmentTrimming3,
                markovSequenceDistributionVD.generate(vdInsertSize),
                markovSequenceDistributionDJ.generate(djInsertSize)
        );
    }

    private VariableDiversityJoiningModel copyAll(boolean fromAccumulator) {
        return new VariableDiversityJoiningModel(
                variableSegmentDistribution.copy(fromAccumulator),
                joiningVariableDistribution.copy(fromAccumulator),
                diversityJoiningVariableDistribution.copy(fromAccumulator),
                joiningTrimmingDistribution.copy(fromAccumulator),
                variableTrimmingDistribution.copy(fromAccumulator),
                diversityTrimming5Distribution.copy(fromAccumulator),
                diversityTrimming3Distribution.copy(fromAccumulator),
                insertSizeDistributionVD.copy(fromAccumulator),
                insertSizeDistributionDJ.copy(fromAccumulator),
                nucleotideDistributionVD.copy(fromAccumulator),
                nucleotideDistributionDJ.copy(fromAccumulator),
                nucleotidePairDistributionVD.copy(fromAccumulator),
                nucleotidePairDistributionDJ.copy(fromAccumulator)
        );
    }

    @Override
    public VariableDiversityJoiningModel getUpdatedModel() {
        return copyAll(true);
    }

    @Override
    public RearrangementModel getModelTemplate() {
        return copyAll(false);
    }

    public VariableDiversityJoiningModel withSegmentDistribution(VariableDistribution variableSegmentDistribution,
                                                                 JoiningVariableDistribution joiningVariableDistribution,
                                                                 DiversityJoiningVariableDistribution diversityJoiningVariableDistribution) {
        // todo: compatibility test
        return new VariableDiversityJoiningModel(
                variableSegmentDistribution,
                joiningVariableDistribution,
                diversityJoiningVariableDistribution,
                joiningTrimmingDistribution.copy(),
                variableTrimmingDistribution.copy(),
                diversityTrimming5Distribution.copy(),
                diversityTrimming3Distribution.copy(),
                insertSizeDistributionVD.copy(),
                insertSizeDistributionDJ.copy(),
                nucleotideDistributionVD.copy(),
                nucleotideDistributionDJ.copy(),
                nucleotidePairDistributionVD.copy(),
                nucleotidePairDistributionDJ.copy()
        );
    }

    public VariableDistribution getVariableSegmentDistribution() {
        return variableSegmentDistribution;
    }

    public JoiningVariableDistribution getJoiningVariableDistribution() {
        return joiningVariableDistribution;
    }

    public DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution() {
        return diversityJoiningVariableDistribution;
    }

    public JoiningTrimmingDistribution getJoiningTrimmingDistribution() {
        return joiningTrimmingDistribution;
    }

    public VariableTrimmingDistribution getVariableTrimmingDistribution() {
        return variableTrimmingDistribution;
    }

    public DiversityTrimming5Distribution getDiversityTrimming5Distribution() {
        return diversityTrimming5Distribution;
    }

    public DiversityTrimming3Distribution getDiversityTrimming3Distribution() {
        return diversityTrimming3Distribution;
    }

    public InsertSizeDistribution getInsertSizeDistributionVD() {
        return insertSizeDistributionVD;
    }

    public InsertSizeDistribution getInsertSizeDistributionDJ() {
        return insertSizeDistributionDJ;
    }

    public NucleotideDistribution getNucleotideDistributionVD() {
        return nucleotideDistributionVD;
    }

    public NucleotideDistribution getNucleotideDistributionDJ() {
        return nucleotideDistributionDJ;
    }

    public NucleotidePairDistribution getNucleotidePairDistributionVD() {
        return nucleotidePairDistributionVD;
    }

    public NucleotidePairDistribution getNucleotidePairDistributionDJ() {
        return nucleotidePairDistributionDJ;
    }
}
