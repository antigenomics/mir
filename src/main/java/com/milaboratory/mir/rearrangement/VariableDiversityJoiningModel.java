package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableDiversityJoiningModel implements RearrangementModel {
    private final VariableDistribution variableSegmentDistribution;
    private final JoiningVariableSegmentDistribution joiningVariableSegmentDistribution;
    private final DiversityJoiningVariableSegmentDistribution diversityJoiningVariableSegmentDistribution;
    private final JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution;
    private final VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution;
    private final DiversitySegmentTrimming5Distribution diversitySegmentTrimming5Distribution;
    private final DiversitySegmentTrimming3Distribution diversitySegmentTrimming3Distribution;
    private final InsertSizeDistribution insertSizeDistributionVD,
            insertSizeDistributionDJ;
    private final NucleotideDistribution nucleotideDistributionVD,
            nucleotideDistributionDJ;
    private final NucleotidePairDistribution nucleotidePairDistributionVD,
            nucleotidePairDistributionDJ;
    private final MarkovNucleotideSequenceDistribution markovSequenceDistributionVD,
            markovSequenceDistributionDJ;

    public VariableDiversityJoiningModel(VariableDistribution variableSegmentDistribution,
                                         JoiningVariableSegmentDistribution joiningVariableSegmentDistribution,
                                         DiversityJoiningVariableSegmentDistribution diversityJoiningVariableSegmentDistribution,
                                         JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution,
                                         VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution,
                                         DiversitySegmentTrimming5Distribution diversitySegmentTrimming5Distribution,
                                         DiversitySegmentTrimming3Distribution diversitySegmentTrimming3Distribution,
                                         InsertSizeDistribution insertSizeDistributionVD,
                                         InsertSizeDistribution insertSizeDistributionDJ,
                                         NucleotideDistribution nucleotideDistributionVD,
                                         NucleotideDistribution nucleotideDistributionDJ,
                                         NucleotidePairDistribution nucleotidePairDistributionVD,
                                         NucleotidePairDistribution nucleotidePairDistributionDJ) {
        this.variableSegmentDistribution = variableSegmentDistribution;
        this.joiningVariableSegmentDistribution = joiningVariableSegmentDistribution;
        this.diversityJoiningVariableSegmentDistribution = diversityJoiningVariableSegmentDistribution;
        this.joiningSegmentTrimmingDistribution = joiningSegmentTrimmingDistribution;
        this.variableSegmentTrimmingDistribution = variableSegmentTrimmingDistribution;
        this.diversitySegmentTrimming5Distribution = diversitySegmentTrimming5Distribution;
        this.diversitySegmentTrimming3Distribution = diversitySegmentTrimming3Distribution;
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
        JoiningSegment joiningSegment = joiningVariableSegmentDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        DiversitySegment diversitySegment = diversityJoiningVariableSegmentDistribution
                .getDistribution0(variableSegment, joiningSegment)
                .getDistributionSampler().generate();

        int variableTrimming = variableSegmentTrimmingDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int joiningTrimming = joiningSegmentTrimmingDistribution.getDistribution0(joiningSegment)
                .getDistributionSampler().generate();
        int diversitySegmentTrimming5 = diversitySegmentTrimming5Distribution.getDistribution0(diversitySegment)
                .getDistributionSampler().generate();
        int diversitySegmentTrimming3 = diversitySegmentTrimming3Distribution
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

    public VariableDistribution getVariableSegmentDistribution() {
        return variableSegmentDistribution;
    }

    public JoiningVariableSegmentDistribution getJoiningVariableSegmentDistribution() {
        return joiningVariableSegmentDistribution;
    }

    public DiversityJoiningVariableSegmentDistribution getDiversityJoiningVariableSegmentDistribution() {
        return diversityJoiningVariableSegmentDistribution;
    }

    public JoiningSegmentTrimmingDistribution getJoiningSegmentTrimmingDistribution() {
        return joiningSegmentTrimmingDistribution;
    }

    public VariableSegmentTrimmingDistribution getVariableSegmentTrimmingDistribution() {
        return variableSegmentTrimmingDistribution;
    }

    public DiversitySegmentTrimming5Distribution getDiversitySegmentTrimming5Distribution() {
        return diversitySegmentTrimming5Distribution;
    }

    public DiversitySegmentTrimming3Distribution getDiversitySegmentTrimming3Distribution() {
        return diversitySegmentTrimming3Distribution;
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
